package ru.nino.mybar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.entity.IngredientAndCount;
import ru.nino.mybar.entity.UnitType;
import ru.nino.mybar.dto.model.PartyInventoryItem;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartyPlanningServiceTest {

    @Mock
    private CocktailServiceImpl cocktailService;

    private PartyPlanningService partyPlanningService;

    @BeforeEach
    void setUp() {
        partyPlanningService = new PartyPlanningService(cocktailService);
    }

    @Test
    void scalesRecipeAndSubtractsInventory() {
        Cocktail cocktail = cocktailWithIngredient("Daiquiri", "White rum", 60);
        when(cocktailService.getApprovedCocktails(List.of(1L))).thenReturn(List.of(cocktail));

        var result = partyPlanningService.calculate(3, List.of(1L), "white rum | 100 | мл");

        assertEquals(1, result.size());
        assertEquals(0, result.get(0).required().compareTo(new BigDecimal("180")));
        assertEquals(0, result.get(0).available().compareTo(new BigDecimal("100")));
        assertEquals(0, result.get(0).missing().compareTo(new BigDecimal("80")));
        assertEquals("180", result.get(0).required().toPlainString());
        assertEquals("мл", result.get(0).unitLabel());
    }

    @Test
    void ignoresInventoryNotUsedBySelectedRecipes() {
        Cocktail cocktail = cocktailWithIngredient("Daiquiri", "White rum", 60);
        when(cocktailService.getApprovedCocktails(List.of(1L))).thenReturn(List.of(cocktail));

        var result = partyPlanningService.calculate(3, List.of(1L), "Vodka | 100 | мл");

        assertEquals(0, result.get(0).available().compareTo(BigDecimal.ZERO));
        assertEquals("180", result.get(0).missing().toPlainString());
    }

    @Test
    void calculatesEachGuestSelectionIndependently() {
        Cocktail daiquiri = cocktailWithIngredient("Daiquiri", "White rum", 60);
        daiquiri.setId(1L);
        Cocktail mojito = cocktailWithIngredient("Mojito", "White rum", 50);
        mojito.setId(2L);
        when(cocktailService.getApprovedCocktails(List.of(1L, 2L))).thenReturn(List.of(daiquiri, mojito));

        var result = partyPlanningService.calculateSelections(
                List.of(List.of(1L, 1L), List.of(2L)),
                List.of(new PartyInventoryItem("white rum", new BigDecimal("80"), UnitType.MILLILITER)));

        assertEquals(1, result.size());
        assertEquals("170", result.get(0).required().toPlainString());
        assertEquals("80", result.get(0).available().toPlainString());
        assertEquals("90", result.get(0).missing().toPlainString());
    }

    @Test
    void recommendsRecipesMissingAtMostThreeIngredients() {
        Cocktail nearMatch = cocktailWithIngredients("Near match", "Rum", "Lime", "Sugar", "Mint");
        nearMatch.setId(10L);
        Cocktail tooManyMissing = cocktailWithIngredients("Too many missing", "Rum", "Lime", "Sugar", "Mint", "Soda");
        tooManyMissing.setId(11L);
        when(cocktailService.getApprovedCocktails()).thenReturn(List.of(nearMatch, tooManyMissing));

        var result = partyPlanningService.recommendCocktails(List.of(1L));

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).id());
        assertEquals(3, result.get(0).missingIngredients().size());
        assertFalse(result.get(0).missingIngredients().contains("Rum"));
    }

    @Test
    void whatToMakeRecommendationsIncludeRecipesMissingFourIngredients() {
        Cocktail missingFour = cocktailWithIngredients("Missing four", "Vodka", "Lime", "Sugar", "Mint", "Soda");
        missingFour.setId(20L);
        Cocktail missingFive = cocktailWithIngredients("Missing five", "Vodka", "Lime", "Sugar", "Mint", "Soda", "Ice");
        missingFive.setId(21L);
        when(cocktailService.getApprovedCocktails()).thenReturn(List.of(missingFive, missingFour));

        var result = partyPlanningService.recommendCocktails(List.of(1L), 4);

        assertEquals(1, result.size());
        assertEquals(20L, result.get(0).id());
        assertEquals(4, result.get(0).missingIngredients().size());
    }

    private Cocktail cocktailWithIngredient(String name, String ingredientName, int amount) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientName);
        Cocktail cocktail = new Cocktail();
        cocktail.setName(name);
        cocktail.setIngredients(List.of(new IngredientAndCount(ingredient, amount, UnitType.MILLILITER)));
        return cocktail;
    }

    private Cocktail cocktailWithIngredients(String name, String... names) {
        List<IngredientAndCount> ingredients = java.util.stream.IntStream.range(0, names.length)
                .mapToObj(index -> {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setId((long) index + 1);
                    ingredient.setName(names[index]);
                    return new IngredientAndCount(ingredient, 20, UnitType.MILLILITER);
                })
                .toList();
        Cocktail cocktail = new Cocktail();
        cocktail.setName(name);
        cocktail.setIngredients(ingredients);
        return cocktail;
    }
}
