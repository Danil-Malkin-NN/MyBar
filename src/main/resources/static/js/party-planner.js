const partyForm = document.querySelector("#party-form");

if (partyForm) {
    const guestSlider = document.querySelector("#guest-count-slider");
    const guestCountLabel = document.querySelector("#guest-count-label");
    const guestCards = document.querySelector("#guest-cards");
    const guestWorkspace = document.querySelector(".guest-workspace");
    const cocktailGallery = document.querySelector("#party-cocktail-gallery");
    const cocktailSearch = document.querySelector("#party-cocktail-search");
    const baseAlcoholFilter = document.querySelector("#party-base-alcohol-filter");
    const activeGuestLabel = document.querySelector("#active-guest-label");
    const cocktailPicker = document.querySelector("#cocktail-picker");
    const inventoryPanel = document.querySelector("#inventory-panel");
    const modeInput = document.querySelector("#party-mode");
    const recommendations = document.querySelector("#pantry-recommendations");
    const recommendationUrl = partyForm.dataset.recommendations;
    const ingredientSearch = document.querySelector("#ingredient-search");
    const ingredientSuggestions = document.querySelector("#ingredient-suggestions");
    const inventoryItems = document.querySelector("#inventory-items");
    const cocktailListUrl = partyForm.dataset.cocktailList;
    const imageBaseUrl = partyForm.dataset.imageBase;
    const ingredientSearchUrl = partyForm.dataset.ingredientSearch;
    let guests = Array.isArray(initialGuests) && initialGuests.length
        ? initialGuests.map(guest => ({name: guest.name, cocktails: [...guest.cocktails]}))
        : [];
    let inventory = Array.isArray(initialInventory)
        ? initialInventory.map(item => ({...item}))
        : [];
    let activeGuestIndex = 0;
    let suppressChipClickUntil = 0;
    const searchTimers = new WeakMap();
    let cocktailOptions;

    const makeElement = (tag, className, text) => {
        const element = document.createElement(tag);
        if (className) element.className = className;
        if (text !== undefined) element.textContent = text;
        return element;
    };

    const search = async (url, query, results, onSelect, source) => {
        results.replaceChildren();
        if (query.trim().length < 2) return;
        const loading = makeElement("p", "suggestion-message", "Ищем в каталоге…");
        results.append(loading);
        try {
            const response = await fetch(`${url}?query=${encodeURIComponent(query.trim())}`, {
                headers: {"Accept": "application/json"}
            });
            if (!response.ok) throw new Error(`Search failed: ${response.status}`);
            const options = await response.json();
            if (source.value.trim() !== query.trim()) return;
            results.replaceChildren();
            if (!options.length) {
                results.append(makeElement("p", "suggestion-message", "Совпадений не найдено."));
                return;
            }
            options.forEach(option => {
                const button = makeElement("button", "suggestion-option", option.name);
                button.type = "button";
                button.setAttribute("role", "option");
                button.addEventListener("click", () => {
                    onSelect(option);
                    results.replaceChildren();
                });
                results.append(button);
            });
        } catch (error) {
            console.error(error);
            if (source.value.trim() !== query.trim()) return;
            results.replaceChildren(makeElement("p", "suggestion-message", "Не удалось выполнить поиск. Попробуйте ещё раз."));
        }
    };

    const captureGuestValues = () => {
        guestCards.querySelectorAll(".guest-card").forEach((card, index) => {
            if (!guests[index]) return;
            guests[index].name = card.querySelector(".guest-name").value;
        });
    };

    const drawGuests = () => {
        guestCards.replaceChildren();
        guests.forEach((guest, index) => {
            const card = makeElement("article", "guest-card");
            if (index === activeGuestIndex && modeInput.value === "guests") card.classList.add("is-active");
            card.addEventListener("click", event => {
                if (event.target.closest("button, input, label")) return;
                captureGuestValues();
                activeGuestIndex = index;
                drawGuests();
                if (!cocktailPicker.hidden && cocktailOptions) drawCocktailGallery();
            });
            card.addEventListener("dragover", event => {
                if (event.dataTransfer?.types.includes("application/x-mybar-cocktail")) {
                    event.preventDefault();
                    card.classList.add("is-drop-target");
                }
            });
            card.addEventListener("dragleave", event => {
                if (!card.contains(event.relatedTarget)) card.classList.remove("is-drop-target");
            });
            card.addEventListener("drop", event => {
                const payload = event.dataTransfer?.getData("application/x-mybar-cocktail");
                if (!payload) return;
                event.preventDefault();
                card.classList.remove("is-drop-target");
                const {sourceIndex, cocktailId} = JSON.parse(payload);
                const sourceCocktail = guests[sourceIndex]?.cocktails.find(item => String(item.id) === cocktailId);
                const targetGuest = guests[index];
                if (!sourceCocktail || !targetGuest
                    || targetGuest.cocktails.some(item => String(item.id) === cocktailId)) return;
                captureGuestValues();
                targetGuest.cocktails.push({...sourceCocktail});
                activeGuestIndex = index;
                drawGuests();
                if (!cocktailPicker.hidden && cocktailOptions) drawCocktailGallery();
            });
            const heading = makeElement("div", "guest-card-heading");
            heading.append(makeElement("span", "guest-number", String(index + 1).padStart(2, "0")));
            const guestTitle = makeElement("h3", "", guest.name?.trim() || `Гость ${index + 1}`);
            heading.append(guestTitle);
            card.append(heading);

            const nameLabel = makeElement("label", "", "Как его/её зовут?");
            const nameInput = makeElement("input", "guest-name");
            nameInput.type = "text";
            nameInput.maxLength = 60;
            nameInput.autocomplete = "off";
            nameInput.placeholder = `Имя гостя ${index + 1}`;
            nameInput.value = guest.name || "";
            nameInput.addEventListener("input", () => {
                guest.name = nameInput.value;
                guestTitle.textContent = guest.name.trim() || `Гость ${index + 1}`;
                updateActiveGuestLabel();
            });
            nameLabel.append(nameInput);
            card.append(nameLabel);
            const addButton = makeElement("button", "add-cocktail-button", "＋ Добавить коктейль");
            addButton.type = "button";
            addButton.addEventListener("click", () => {
                captureGuestValues();
                activeGuestIndex = index;
                drawGuests();
                cocktailPicker.hidden = false;
                guestWorkspace.classList.add("has-picker");
                loadCocktailGallery();
            });
            card.append(addButton);
            const chips = makeElement("div", "cocktail-chips");
            card.append(chips);

            guest.cocktails.forEach(cocktail => {
                const chip = makeElement("button", "cocktail-chip");
                chip.type = "button";
                chip.dataset.id = cocktail.id;
                chip.dataset.name = cocktail.name;
                chip.setAttribute("aria-label", `Убрать ${cocktail.name}`);
                chip.append(makeElement("span", "cocktail-chip-name", cocktail.name));
                chip.append(makeElement("span", "cocktail-chip-remove", "×"));
                chip.draggable = true;
                chip.title = "Перетащите на другого гостя, чтобы добавить ему";
                chip.addEventListener("dragstart", event => {
                    suppressChipClickUntil = Date.now() + 500;
                    event.dataTransfer.effectAllowed = "copy";
                    event.dataTransfer.setData("application/x-mybar-cocktail",
                        JSON.stringify({sourceIndex: index, cocktailId: String(cocktail.id)}));
                });
                chip.addEventListener("click", () => {
                    if (Date.now() < suppressChipClickUntil) return;
                    const chipIndex = [...chips.children].indexOf(chip);
                    captureGuestValues();
                    guests[index].cocktails.splice(chipIndex, 1);
                    drawGuests();
                });
                chips.append(chip);
            });
            card.append(makeElement("p", "guest-hint", "Каждый выбранный напиток считается одной порцией."));
            guestCards.append(card);
        });
        updateActiveGuestLabel();
    };

    const updateActiveGuestLabel = () => {
        const guest = guests[activeGuestIndex];
        activeGuestLabel.textContent = guest
            ? `Сейчас выбран: ${guest.name?.trim() || `Гость ${activeGuestIndex + 1}`}`
            : "Добавьте гостя, чтобы выбрать напитки.";
    };

    const loadCocktailGallery = async () => {
        if (cocktailOptions) {
            drawCocktailGallery();
            return;
        }
        cocktailGallery.replaceChildren(makeElement("p", "suggestion-message", "Загружаем коктейли…"));
        try {
            const response = await fetch(cocktailListUrl, {headers: {"Accept": "application/json"}});
            if (!response.ok) throw new Error(`Cocktail list request failed: ${response.status}`);
            cocktailOptions = await response.json();
            fillBaseAlcoholFilter();
            drawCocktailGallery();
        } catch (error) {
            console.error(error);
            cocktailGallery.replaceChildren(makeElement("p", "suggestion-message", "Не удалось загрузить коктейли."));
        }
    };

    const drawCocktailGallery = () => {
        cocktailGallery.replaceChildren();
        if (!cocktailOptions) return;
        if (!cocktailOptions.length) {
            cocktailGallery.append(makeElement("p", "suggestion-message", "Пока нет доступных коктейлей."));
            return;
        }
        const query = cocktailSearch.value.trim().toLocaleLowerCase("ru");
        const baseAlcohol = baseAlcoholFilter.value;
        const filteredOptions = cocktailOptions.filter(option =>
            option.name.toLocaleLowerCase("ru").includes(query)
            && (!baseAlcohol || (option.baseAlcohol || "").trim() === baseAlcohol));
        if (!filteredOptions.length) {
            cocktailGallery.append(makeElement("p", "suggestion-message", "По этим фильтрам коктейли не найдены."));
            return;
        }
        const selectedGuest = guests[activeGuestIndex];
        filteredOptions.forEach(option => {
            const alreadyAdded = selectedGuest?.cocktails.some(cocktail => cocktail.id === option.id) || false;
            const card = makeElement("button", "cocktail-choice", "");
            card.type = "button";
            card.disabled = alreadyAdded;
            card.setAttribute("role", "listitem");
            card.setAttribute("aria-label", alreadyAdded ? `${option.name}, уже добавлен` : `Добавить ${option.name}`);
            const image = option.image
                ? makeElement("img", "cocktail-choice-image")
                : makeElement("span", "cocktail-choice-placeholder", "🍸");
            if (option.image) {
                image.src = `${imageBaseUrl}${option.image}`;
                image.alt = "";
                image.loading = "lazy";
                image.addEventListener("error", () => {
                    card.replaceChild(makeElement("span", "cocktail-choice-placeholder", "🍸"), image);
                }, {once: true});
            }
            card.append(image);
            card.append(makeElement("span", "cocktail-choice-name", option.name));
            if (alreadyAdded) card.append(makeElement("span", "cocktail-choice-selected", "Добавлен"));
            card.addEventListener("click", () => {
                captureGuestValues();
                const guest = guests[activeGuestIndex];
                if (!guest || guest.cocktails.some(cocktail => cocktail.id === option.id)) return;
                guest.cocktails.push({id: option.id, name: option.name});
                drawGuests();
                drawCocktailGallery();
            });
            cocktailGallery.append(card);
        });
    };

    const fillBaseAlcoholFilter = () => {
        const alcohols = [...new Set(cocktailOptions
            .map(option => option.baseAlcohol?.trim())
            .filter(Boolean))]
            .sort((left, right) => left.localeCompare(right, "ru"));
        alcohols.forEach(alcohol => {
            const option = makeElement("option", "", alcohol);
            option.value = alcohol;
            baseAlcoholFilter.append(option);
        });
    };

    cocktailSearch.addEventListener("input", drawCocktailGallery);
    baseAlcoholFilter.addEventListener("change", drawCocktailGallery);

    const loadRecommendations = async () => {
        const ids = inventory.map(item => item.id);
        recommendations.replaceChildren();
        if (!ids.length) {
            recommendations.append(makeElement("p", "suggestion-message", "Добавьте ингредиенты, чтобы получить идеи."));
            return;
        }
        recommendations.append(makeElement("p", "suggestion-message", "Подбираем рецепты…"));
        try {
            const query = ids.map(id => `ingredientIds=${encodeURIComponent(id)}`).join("&");
            const response = await fetch(`${recommendationUrl}?${query}`, {headers: {"Accept": "application/json"}});
            if (!response.ok) throw new Error(`Recommendation request failed: ${response.status}`);
            const options = await response.json();
            recommendations.replaceChildren();
            if (!options.length) {
                recommendations.append(makeElement("p", "suggestion-message", "Пока нет рецептов с такими запасами."));
                return;
            }
            options.forEach(option => {
                const item = makeElement("article", "pantry-recommendation");
                const top = makeElement("div", "pantry-recommendation-top");
                const title = makeElement("strong", "", option.name);
                const selectedGuest = guests[activeGuestIndex];
                const alreadyAdded = selectedGuest?.cocktails.some(cocktail => cocktail.id === option.id) || false;
                const add = makeElement("button", "pantry-add-button", alreadyAdded ? "Уже добавлен" : "Добавить гостю");
                add.type = "button";
                add.disabled = alreadyAdded;
                add.addEventListener("click", () => {
                    captureGuestValues();
                    const selectedGuest = guests[activeGuestIndex];
                    if (selectedGuest && !selectedGuest.cocktails.some(cocktail => cocktail.id === option.id)) {
                        selectedGuest.cocktails.push({id: option.id, name: option.name});
                        drawGuests();
                    }
                    add.textContent = "Добавлено";
                    add.disabled = true;
                });
                top.append(title, add);
                item.append(top);
                const needs = option.missingIngredients || [];
                item.append(makeElement("p", "muted", needs.length
                    ? `Докупить: ${needs.join(", ")}`
                    : "Все ингредиенты уже есть"));
                recommendations.append(item);
            });
        } catch (error) {
            console.error(error);
            recommendations.replaceChildren(makeElement("p", "suggestion-message", "Не удалось загрузить рекомендации."));
        }
    };

    const drawInventory = () => {
        inventoryItems.replaceChildren();
        inventory.forEach((item, index) => {
            const row = makeElement("div", "inventory-item");
            row.dataset.id = item.id;
            const title = makeElement("div", "inventory-item-title");
            const icon = makeElement("span", "entity-icon ingredient-icon", "✳");
            icon.dataset.iconKind = "ingredient";
            icon.dataset.iconName = item.name;
            title.append(icon);
            title.append(makeElement("strong", "", item.name));
            const remove = makeElement("button", "remove-item", "Убрать");
            remove.type = "button";
            remove.addEventListener("click", () => {
                captureInventoryValues();
                inventory.splice(index, 1);
                drawInventory();
                loadRecommendations();
            });
            title.append(remove);
            row.append(title);

            const controls = makeElement("div", "inventory-controls");
            const amount = makeElement("input", "inventory-amount");
            amount.type = "number";
            amount.min = "0";
            amount.step = "0.1";
            amount.required = true;
            amount.value = item.amount || "";
            amount.placeholder = "Количество";
            amount.setAttribute("aria-label", `Количество ${item.name}`);
            controls.append(amount);
            const unit = makeElement("select", "inventory-unit");
            unit.setAttribute("aria-label", `Единица измерения ${item.name}`);
            [["MILLILITER", "мл"], ["GRAM", "г"], ["PIECES", "шт."]].forEach(([value, label]) => {
                const option = makeElement("option", "", label);
                option.value = value;
                option.selected = item.unit === value;
                unit.append(option);
            });
            controls.append(unit);
            row.append(controls);
            inventoryItems.append(row);
            window.applyMyBarIcons?.(row);
        });
    };

    const captureInventoryValues = () => {
        inventoryItems.querySelectorAll(".inventory-item").forEach((row, index) => {
            if (!inventory[index]) return;
            inventory[index].amount = row.querySelector(".inventory-amount").value;
            inventory[index].unit = row.querySelector(".inventory-unit").value;
        });
    };

    document.querySelectorAll('input[name="alcoholStatus"]').forEach(radio => {
        radio.addEventListener("change", () => {
            inventoryPanel.hidden = radio.value !== "yes" || !radio.checked;
            if (radio.checked && radio.value === "yes") ingredientSearch.focus();
        });
    });

    document.querySelector("#close-cocktail-picker")?.addEventListener("click", () => {
        cocktailPicker.hidden = true;
        guestWorkspace.classList.remove("has-picker");
    });

    guestSlider.addEventListener("input", () => {
        captureGuestValues();
        const targetCount = Number(guestSlider.value);
        while (guests.length < targetCount) {
            guests.push({name: "", cocktails: []});
        }
        guests.length = targetCount;
        activeGuestIndex = Math.min(activeGuestIndex, targetCount - 1);
        guestCountLabel.value = targetCount;
        guestCountLabel.textContent = targetCount;
        updateSliderTrack();
        drawGuests();
    });

    ingredientSearch.addEventListener("input", () => {
        clearTimeout(searchTimers.get(ingredientSearch));
        const query = ingredientSearch.value;
        searchTimers.set(ingredientSearch, setTimeout(() => search(ingredientSearchUrl, query, ingredientSuggestions, option => {
            const existing = inventory.find(item => item.id === option.id);
            if (existing) {
                inventoryItems.querySelector(`[data-id="${option.id}"] .inventory-amount`)?.focus();
                return;
            }
            captureInventoryValues();
            inventory.push({id: option.id, name: option.name, amount: "", unit: "MILLILITER"});
            ingredientSearch.value = "";
            drawInventory();
            loadRecommendations();
        }, ingredientSearch), 220));
    });

    partyForm.addEventListener("submit", () => {
        captureGuestValues();
        captureInventoryValues();
        partyForm.querySelectorAll("[data-generated-field]").forEach(field => field.remove());
        const addHidden = (name, value) => {
            const input = makeElement("input");
            input.type = "hidden";
            input.name = name;
            input.value = value;
            input.dataset.generatedField = "true";
            partyForm.append(input);
        };
        guests.forEach(guest => {
            addHidden("guestNames", guest.name || "");
            addHidden("guestCocktailIds", guest.cocktails.map(cocktail => cocktail.id).join(","));
        });
        const hasAlcohol = document.querySelector('input[name="alcoholStatus"]:checked')?.value === "yes";
        if (hasAlcohol) {
            inventoryItems.querySelectorAll(".inventory-item").forEach(row => {
                addHidden("ingredientIds", row.dataset.id);
                addHidden("ingredientAmounts", row.querySelector(".inventory-amount").value);
                addHidden("ingredientUnits", row.querySelector(".inventory-unit").value);
            });
        }
    });

    document.querySelector("#save-shopping-list")?.addEventListener("click", () => {
        const items = [...document.querySelectorAll("#party-shopping-list .shopping-item")]
            .filter(item => item.querySelector("strong"))
            .map(item => ({
                name: item.querySelector("h3").textContent,
                amount: item.querySelector("strong").textContent.replace(/^\+\s*/, ""),
                unit: item.dataset.unit,
                checked: false
            }));
        let saved;
        try {
            const parsed = JSON.parse(localStorage.getItem("mybar-shopping-list") || "[]");
            saved = Array.isArray(parsed) ? parsed : [];
        } catch (error) {
            console.error("Unable to read shopping list", error);
            window.alert("Не удалось прочитать сохранённый список покупок. Очистите повреждённые данные браузера и попробуйте снова.");
            return;
        }
        const combined = new Map(saved.map(item => [`${item.name}|${item.unit || ""}`, item]));
        items.forEach(item => {
            const key = `${item.name}|${item.unit}`;
            combined.set(key, {...combined.get(key), ...item});
        });
        localStorage.setItem("mybar-shopping-list", JSON.stringify([...combined.values()]));
        window.location.href = partyForm.dataset.shoppingList;
    });

    drawGuests();
    drawInventory();
    const alcoholStatus = inventory.length ? "yes" : "no";
    document.querySelector(`input[name="alcoholStatus"][value="${alcoholStatus}"]`).checked = true;
    inventoryPanel.hidden = alcoholStatus !== "yes";
    guestCountLabel.value = guests.length;
    guestCountLabel.textContent = guests.length;
    const updateSliderTrack = () => {
        const range = Number(guestSlider.max) - Number(guestSlider.min);
        const progress = range > 0 ? (Number(guestSlider.value) - Number(guestSlider.min)) / range * 100 : 0;
        guestSlider.style.setProperty("--slider-progress", `${progress}%`);
    };
    updateSliderTrack();
    loadRecommendations();
}
