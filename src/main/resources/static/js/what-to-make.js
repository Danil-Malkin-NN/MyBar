const makePage = document.querySelector(".what-to-make-page");

if (makePage) {
    const ingredientSearch = document.querySelector("#make-ingredient-search");
    const suggestions = document.querySelector("#make-ingredient-suggestions");
    const stockItems = document.querySelector("#make-stock-items");
    const readyCocktails = document.querySelector("#ready-cocktails");
    const missingSection = document.querySelector("#missing-cocktails-section");
    const missingCocktails = document.querySelector("#missing-cocktails");
    const resultsCount = document.querySelector("#make-results-count");
    const selectedIngredients = new Map();
    const ingredientSearchUrl = makePage.dataset.ingredientSearch;
    const recommendationsUrl = makePage.dataset.recommendations;
    const imageBaseUrl = makePage.dataset.imageBase;
    let searchTimer;
    let recommendationSequence = 0;

    const element = (tag, className, text) => {
        const node = document.createElement(tag);
        if (className) node.className = className;
        if (text !== undefined) node.textContent = text;
        return node;
    };

    const renderStock = () => {
        stockItems.replaceChildren();
        selectedIngredients.forEach(ingredient => {
            const chip = element("span", "make-stock-chip");
            chip.append(element("span", "", ingredient.name));
            const remove = element("button", "make-stock-remove", "×");
            remove.type = "button";
            remove.setAttribute("aria-label", `Убрать ${ingredient.name}`);
            remove.addEventListener("click", () => {
                selectedIngredients.delete(ingredient.id);
                renderStock();
                loadRecommendations();
            });
            chip.append(remove);
            stockItems.append(chip);
        });
    };

    const addIngredient = ingredient => {
        if (selectedIngredients.has(ingredient.id)) return;
        selectedIngredients.set(ingredient.id, ingredient);
        ingredientSearch.value = "";
        suggestions.replaceChildren();
        renderStock();
        loadRecommendations();
    };

    const searchIngredients = async query => {
        suggestions.replaceChildren();
        if (query.trim().length < 2) return;
        suggestions.append(element("p", "suggestion-message", "Ищем ингредиенты…"));
        try {
            const response = await fetch(`${ingredientSearchUrl}?query=${encodeURIComponent(query.trim())}`,
                {headers: {"Accept": "application/json"}});
            if (!response.ok) throw new Error(`Ingredient search failed: ${response.status}`);
            const options = await response.json();
            if (ingredientSearch.value.trim() !== query.trim()) return;
            suggestions.replaceChildren();
            const availableOptions = options.filter(option => !selectedIngredients.has(option.id));
            if (!availableOptions.length) {
                suggestions.append(element("p", "suggestion-message", "Ничего не найдено или уже добавлено."));
                return;
            }
            availableOptions.forEach(option => {
                const button = element("button", "suggestion-option", option.name);
                button.type = "button";
                button.setAttribute("role", "option");
                button.addEventListener("click", () => addIngredient(option));
                suggestions.append(button);
            });
        } catch (error) {
            console.error(error);
            suggestions.replaceChildren(element("p", "suggestion-message", "Не удалось найти ингредиенты."));
        }
    };

    const renderCocktail = cocktail => {
        const card = element("article", "card cocktail-card make-cocktail-card");
        if (cocktail.image) {
            const image = element("img", "card-image cocktail-image");
            image.src = `${imageBaseUrl}${cocktail.image}`;
            image.alt = "";
            image.loading = "lazy";
            image.addEventListener("error", () => image.remove(), {once: true});
            card.append(image);
        } else {
            const placeholder = element("div", "cocktail-image-placeholder", "🍸");
            placeholder.setAttribute("aria-hidden", "true");
            card.append(placeholder);
        }
        const content = element("div", "card-content");
        content.append(element("h3", "card-title", cocktail.name));
        const meta = element("div", "meta");
        if (cocktail.strength > 0) meta.append(element("span", "tag", `${cocktail.strength}%`));
        if (cocktail.volume > 0) meta.append(element("span", "tag", `${cocktail.volume} мл`));
        content.append(meta);
        card.append(content);
        return card;
    };

    const loadRecommendations = async () => {
        const sequence = ++recommendationSequence;
        const ids = [...selectedIngredients.keys()];
        readyCocktails.replaceChildren();
        missingCocktails.replaceChildren();
        missingSection.hidden = true;
        if (!ids.length) {
            resultsCount.textContent = "Добавьте ингредиенты, чтобы увидеть варианты.";
            return;
        }
        resultsCount.textContent = "Подбираем коктейли…";
        const query = ids.map(id => `ingredientIds=${encodeURIComponent(id)}`).join("&");
        try {
            const response = await fetch(`${recommendationsUrl}?${query}`,
                {headers: {"Accept": "application/json"}});
            if (!response.ok) throw new Error(`Recommendations request failed: ${response.status}`);
            const recommendations = await response.json();
            if (sequence !== recommendationSequence) return;
            const ready = recommendations.filter(item => item.missingIngredients.length === 0);
            const toBuy = recommendations.filter(item => item.missingIngredients.length > 0);
            ready.forEach(item => readyCocktails.append(renderCocktail(item)));
            toBuy.forEach(item => {
                const card = renderCocktail(item);
                const content = card.querySelector(".card-content");
                const missing = element("p", "make-missing-ingredients",
                    `Нужно докупить: ${item.missingIngredients.join(", ")}`);
                content.append(missing);
                missingCocktails.append(card);
            });
            missingSection.hidden = !toBuy.length;
            if (!recommendations.length) {
                resultsCount.textContent = "Пока не нашли рецепты с этими запасами.";
                readyCocktails.append(element("p", "suggestion-message",
                    "Добавьте больше ингредиентов — появятся новые варианты."));
            } else {
                resultsCount.textContent = `${ready.length} можно приготовить · ${toBuy.length} с покупками`;
                if (!ready.length) {
                    readyCocktails.append(element("p", "suggestion-message",
                        "Пока нет рецептов, которые можно приготовить без покупок."));
                }
            }
        } catch (error) {
            console.error(error);
            if (sequence !== recommendationSequence) return;
            resultsCount.textContent = "Не удалось загрузить рекомендации. Попробуйте ещё раз.";
        }
    };

    ingredientSearch.addEventListener("input", () => {
        clearTimeout(searchTimer);
        const query = ingredientSearch.value;
        searchTimer = setTimeout(() => searchIngredients(query), 220);
    });

    loadRecommendations();
}
