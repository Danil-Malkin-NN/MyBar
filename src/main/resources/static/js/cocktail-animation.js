const motions = [
    {name: "strain", pattern: /strain|straining|fine.?strain|процед|отфильтр|стрейн/},
    {name: "shake", pattern: /shake|shaking|встрях|шейк|шейкер/},
    {name: "muddle", pattern: /muddle|muddling|разомн|растол|мадл/},
    {name: "squeeze", pattern: /squeeze|press|выжм|отожм|цитрус.?пресс/},
    {name: "garnish", pattern: /garnish|decorate|украс|цедр|дольк|гарнир/},
    {name: "stir", pattern: /stir|stirring|перемеш|размеш|барн.*ложк/},
    {name: "layer", pattern: /layer|слоями|слой|по стенк/},
    {name: "pour", pattern: /pour|add|полн|влей|налей|добав/}
];

const recipeMotions = [
    {pattern: /mojito|мохито/i, sequence: ["muddle", "pour", "stir", "garnish"]},
    {pattern: /margarita|маргарита/i, sequence: ["squeeze", "shake", "strain", "garnish"]},
    {pattern: /daiquiri|дайкири/i, sequence: ["squeeze", "shake", "strain"]},
    {pattern: /negroni|негрони/i, sequence: ["pour", "stir", "garnish"]},
    {pattern: /old.?fashioned|олд.?фэшн/i, sequence: ["muddle", "pour", "stir"]},
    {pattern: /martini|мартини/i, sequence: ["pour", "stir", "strain", "garnish"]},
    {pattern: /pina.?colada|пина.?колада/i, sequence: ["pour", "shake", "strain"]},
    {pattern: /moscow.?mule|московский.?мул/i, sequence: ["build", "stir", "garnish"]},
    {pattern: /spritz|спритц/i, sequence: ["build", "pour", "garnish"]}
];

document.querySelectorAll(".recipe-step").forEach((step, index) => {
    const scene = step.querySelector(".step-visual");
    const detail = step.closest("[data-cocktail-name]");
    const recipeName = detail?.dataset.cocktailName || "";
    const actionText = [
        step.querySelector("h3")?.textContent,
        step.querySelector(".muted")?.textContent,
        ...[...step.querySelectorAll("li")].map(item => item.textContent)
    ].join(" ").toLowerCase();
    const explicitMotion = motions.find(motion => motion.pattern.test(actionText))?.name;
    const recipe = recipeMotions.find(item => item.pattern.test(recipeName));
    const sequence = recipe?.sequence;
    const motion = explicitMotion || sequence?.[index % sequence.length] || (index === 0 ? "build" : "pour");
    scene.dataset.motion = motion;
});
