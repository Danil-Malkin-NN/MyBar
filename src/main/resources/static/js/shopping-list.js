const storageKey = "mybar-shopping-list";
const list = document.querySelector("#saved-shopping-list");
const clearButton = document.querySelector("#clear-shopping-list");

if (list) {
    const readItems = () => {
        try {
            const stored = JSON.parse(localStorage.getItem(storageKey) || "[]");
            if (!Array.isArray(stored) || stored.some(item => !item || typeof item.name !== "string")) {
                throw new Error("Saved shopping list has an invalid format.");
            }
            return stored;
        } catch (error) {
            console.error("Unable to read shopping list", error);
            return null;
        }
    };

    const render = () => {
        const items = readItems();
        list.replaceChildren();
        if (items === null) {
            const error = document.createElement("p");
            error.className = "empty-message";
            error.setAttribute("role", "alert");
            error.textContent = "Сохранённый список повреждён. Очистите его, чтобы начать заново.";
            list.append(error);
            return;
        }
        if (!items.length) {
            const empty = document.createElement("p");
            empty.className = "empty-message";
            empty.textContent = "Список пока пуст. Рассчитайте покупки для тусовки и сохраните недостающие продукты.";
            list.append(empty);
            return;
        }
        items.forEach((item, index) => {
            const row = document.createElement("label");
            row.className = "saved-shopping-item";
            const checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.checked = Boolean(item.checked);
            checkbox.addEventListener("change", () => {
                items[index].checked = checkbox.checked;
                localStorage.setItem(storageKey, JSON.stringify(items));
                row.classList.toggle("is-checked", checkbox.checked);
            });
            const name = document.createElement("strong");
            name.textContent = item.name;
            const amount = document.createElement("span");
            amount.textContent = item.amount || "";
            row.append(checkbox, name, amount);
            row.classList.toggle("is-checked", checkbox.checked);
            list.append(row);
        });
    };

    clearButton?.addEventListener("click", () => {
        localStorage.removeItem(storageKey);
        render();
    });
    render();
}
