const ingredientArt = [
    [/lime|lemon|citrus|orange|grapefruit|лайм|лимон|цитрус|апельсин|грейпфрут/i, "ingredient-citrus.svg"],
    [/berry|cherry|cranberry|ягод|вишн|клюкв|apple|pear|ягод|яблок|груш/i, "ingredient-fruit.svg"],
    [/mint|basil|herb|мят|базилик|трав/i, "ingredient-herbs.svg"],
    [/ice|л[её]д/i, "ingredient-ice.svg"],
    [/syrup|сироп|honey|мёд|мед|sugar|сахар/i, "ingredient-syrup.svg"],
    [/vodka|gin|rum|whisk|tequila|brandy|cognac|liqueur|вермут|водка|джин|ром|виски|текил|коньяк|лик[её]р/i, "ingredient-bottle.svg"]
];

const toolArt = [
    [/shaker|шейкер/i, "tool-shaker.svg"],
    [/spoon|ложк|muddler|мадлер/i, "tool-spoon.svg"],
    [/jigger|джиггер|мерн.*стакан/i, "tool-jigger.svg"],
    [/strainer|ситечк|стрейнер/i, "tool-strainer.svg"],
    [/glass|бокал|стакан/i, "tool-jigger.svg"],
    [/blender|блендер|press|пресс|knife|нож/i, "tool-spoon.svg"]
];

const iconScriptUrl = document.currentScript?.src;
window.applyMyBarIcons = (root = document) => {
    root.querySelectorAll(".entity-icon[data-icon-name], img.entity-art[data-icon-name]").forEach(icon => {
        const name = icon.dataset.iconName;
        const dictionary = icon.dataset.iconKind === "tool" ? toolArt : ingredientArt;
        const imageName = dictionary.find(([pattern]) => pattern.test(name))?.[1]
            || (icon.dataset.iconKind === "tool" ? "tool-shaker.svg" : "ingredient-fruit.svg");
        if (icon.tagName === "IMG") {
            icon.src = new URL(`../images/${imageName}`, iconScriptUrl || window.location.href).href;
        } else {
            const image = document.createElement("img");
            image.src = new URL(`../images/${imageName}`, iconScriptUrl || window.location.href).href;
            image.alt = "";
            image.className = `entity-art ${icon.dataset.iconKind}-art`;
            icon.replaceWith(image);
        }
    });
};

window.applyMyBarIcons();

document.querySelectorAll(".ingredient-toggle").forEach(button => {
    button.addEventListener("click", () => {
        const description = button.parentElement.querySelector(".detail-row-description");
        const expanded = button.getAttribute("aria-expanded") === "true";
        button.setAttribute("aria-expanded", String(!expanded));
        description.hidden = expanded;
    });
});
