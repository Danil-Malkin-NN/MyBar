package ru.nino.mybar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nino.mybar.dto.user.CocktailSubmissionForm;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.CocktailStatus;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.entity.IngredientAndCount;
import ru.nino.mybar.entity.Step;
import ru.nino.mybar.entity.UnitType;
import ru.nino.mybar.repository.impl.CocktailRepositoryImpl;
import ru.nino.mybar.repository.impl.IngredientRepositoryImpl;

@Service
@RequiredArgsConstructor
public class CocktailSubmissionService {
    private final CocktailRepositoryImpl cocktailRepository;
    private final IngredientRepositoryImpl ingredientRepository;

    @Transactional
    public Cocktail submit(CocktailSubmissionForm form, String author) {
        Cocktail cocktail = new Cocktail();
        cocktail.setName(form.getName().trim());
        cocktail.setDescription(form.getDescription().trim());
        cocktail.setBaseAlcohol(form.getBaseAlcohol().trim());
        cocktail.setStrength(form.getStrength());
        cocktail.setVolume(form.getVolume());
        cocktail.setSubmittedBy(author);
        cocktail.setStatus(CocktailStatus.PENDING);

        String[] ingredientLines = form.getIngredients().split("\\R");
        for (int index = 0; index < ingredientLines.length; index++) {
            String line = ingredientLines[index].trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\|");
            if (parts.length != 3 || parts[0].isBlank()) {
                throw new IllegalArgumentException("Строка ингредиента " + (index + 1)
                        + ": используйте формат «название | количество | мл/г/шт».");
            }
            int count;
            try {
                count = Integer.parseInt(parts[1].trim());
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Строка ингредиента " + (index + 1)
                        + ": количество должно быть целым числом.");
            }
            if (count <= 0) {
                throw new IllegalArgumentException("Количество ингредиента должно быть больше нуля.");
            }
            UnitType unitType = parseUnit(parts[2].trim(), index + 1);
            String name = parts[0].trim();
            Ingredient ingredient = ingredientRepository.findByName(name);
            if (ingredient == null) {
                ingredient = new Ingredient();
                ingredient.setName(name);
                ingredient.setDescription("");
                ingredient = ingredientRepository.save(ingredient);
            }
            cocktail.getIngredients().add(new IngredientAndCount(ingredient, count, unitType));
        }
        if (cocktail.getIngredients().isEmpty()) {
            throw new IllegalArgumentException("Добавьте хотя бы один ингредиент.");
        }

        String[] stepLines = form.getSteps().split("\\R");
        int stepNumber = 1;
        for (String line : stepLines) {
            String description = line.trim();
            if (!description.isEmpty()) {
                Step step = new Step();
                step.setGoal("Шаг " + stepNumber++);
                step.setDescription(description);
                cocktail.getSteps().add(step);
            }
        }
        if (cocktail.getSteps().isEmpty()) {
            throw new IllegalArgumentException("Добавьте хотя бы один шаг приготовления.");
        }
        return cocktailRepository.save(cocktail);
    }

    private UnitType parseUnit(String input, int line) {
        return switch (input.toLowerCase(java.util.Locale.ROOT)) {
            case "ml", "мл", "milliliter", "milliliters" -> UnitType.MILLILITER;
            case "g", "гр", "г", "gram", "grams" -> UnitType.GRAM;
            case "pc", "pcs", "шт", "piece", "pieces" -> UnitType.PIECES;
            default -> throw new IllegalArgumentException("Строка ингредиента " + line
                    + ": единица должна быть мл, г или шт.");
        };
    }
}
