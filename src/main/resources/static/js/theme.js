(function () {
    const root = document.documentElement;
    const savedTheme = localStorage.getItem("mybar-theme");
    const preferredTheme = "dark";
    root.dataset.theme = savedTheme || preferredTheme;

    const button = document.getElementById("theme-toggle");
    if (!button) {
        return;
    }
    const updateButton = () => {
        const dark = root.dataset.theme === "dark";
        button.textContent = dark ? "☼" : "◐";
        button.setAttribute("aria-label", dark ? "Включить светлую тему" : "Включить тёмную тему");
    };
    updateButton();
    button.addEventListener("click", () => {
        root.dataset.theme = root.dataset.theme === "dark" ? "light" : "dark";
        localStorage.setItem("mybar-theme", root.dataset.theme);
        updateButton();
    });
})();
