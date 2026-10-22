package ru.nino.mybar.controller.mwc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.nino.mybar.dto.model.PartyIngredient;
import ru.nino.mybar.dto.model.PartyInventoryItem;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.entity.UnitType;
import ru.nino.mybar.repository.impl.IngredientRepositoryImpl;
import ru.nino.mybar.service.CocktailServiceImpl;
import ru.nino.mybar.service.IngredientServiceImpl;
import ru.nino.mybar.service.PartyPlanningService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/page/party")
@RequiredArgsConstructor
public class PartyController {
    private final CocktailServiceImpl cocktailService;
    private final IngredientServiceImpl ingredientService;
    private final IngredientRepositoryImpl ingredientRepository;
    private final PartyPlanningService partyPlanningService;

    @GetMapping
    public String party(Model model) {
        model.addAttribute("guests", 6);
        model.addAttribute("guestForms", defaultGuests(6));
        model.addAttribute("inventoryForms", List.of());
        return "party";
    }

    @PostMapping
    public String calculate(@RequestParam int guests,
                            @RequestParam(defaultValue = "guests") String mode,
                            @RequestParam(required = false) List<String> guestNames,
                            @RequestParam(required = false) List<String> guestCocktailIds,
                            @RequestParam(required = false) String pantryCocktailIds,
                            @RequestParam(required = false) List<Long> ingredientIds,
                            @RequestParam(required = false) List<String> ingredientAmounts,
                            @RequestParam(required = false) List<String> ingredientUnits,
                            Model model) {
        List<GuestView> guestForms = defaultGuests(guests);
        List<InventoryView> inventoryForms = List.of();
        model.addAttribute("guests", guests);
        try {
            if (guests < 1 || guests > 20) {
                throw new IllegalArgumentException("Укажите от 1 до 20 гостей.");
            }
            if (guestNames == null || guestNames.size() != guests
                    || (mode.equals("guests")
                    && (guestCocktailIds == null || guestCocktailIds.size() != guests))) {
                throw new IllegalArgumentException("Обновите страницу и заполните карточки гостей.");
            }

            if (!mode.equals("guests") && !mode.equals("pantry")) {
                throw new IllegalArgumentException("Выберите один из режимов планирования.");
            }
            List<List<Long>> selections = new ArrayList<>();
            List<String> names = new ArrayList<>();
            for (int index = 0; index < guests; index++) {
                names.add(guestNames.get(index).isBlank() ? "Гость " + (index + 1) : guestNames.get(index).trim());
                selections.add(mode.equals("pantry")
                        ? parseCocktailIds(pantryCocktailIds)
                        : parseCocktailIds(guestCocktailIds.get(index)));
            }
            guestForms = createGuestViews(names, selections);

            inventoryForms = createInventoryViews(ingredientIds, ingredientAmounts, ingredientUnits);
            List<PartyInventoryItem> inventory = parseInventory(ingredientIds, ingredientAmounts,
                    ingredientUnits);
            List<PartyIngredient> ingredients = partyPlanningService.calculateSelections(selections, inventory);
            model.addAttribute("ingredients", ingredients);
            model.addAttribute("selectedCount", selections.stream().mapToInt(List::size).sum());
        } catch (IllegalArgumentException exception) {
            model.addAttribute("error", exception.getMessage());
        }
        model.addAttribute("guestForms", guestForms);
        model.addAttribute("inventoryForms", inventoryForms);
        return "party";
    }

    @GetMapping("/cocktails")
    @ResponseBody
    public List<PartyOption> cocktails() {
        return cocktailService.getApprovedCocktails().stream()
                .map(this::toPartyCocktail)
                .toList();
    }

    @GetMapping("/search/ingredients")
    @ResponseBody
    public List<PartyOption> searchIngredients(@RequestParam(defaultValue = "") String query) {
        if (query.trim().length() < 2) {
            return List.of();
        }
        return ingredientService.searchIngredients(query).stream()
                .map(ingredient -> new PartyOption(ingredient.getId(), ingredient.getName(), null, null))
                .toList();
    }

    @GetMapping("/recommendations")
    @ResponseBody
    public List<PartyPlanningService.PartyRecommendation> recommendations(
            @RequestParam(required = false) List<Long> ingredientIds) {
        return partyPlanningService.recommendCocktails(ingredientIds);
    }

    private List<GuestView> defaultGuests(int count) {
        int safeCount = Math.max(0, Math.min(count, 20));
        List<GuestView> guests = new ArrayList<>();
        for (int index = 1; index <= safeCount; index++) {
            guests.add(new GuestView("Гость " + index, List.of()));
        }
        return guests;
    }

    private List<Long> parseCocktailIds(String values) {
        if (values == null || values.isBlank()) {
            return List.of();
        }
        try {
            return java.util.Arrays.stream(values.split(","))
                    .filter(value -> !value.isBlank())
                    .map(Long::valueOf)
                    .toList();
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Не удалось распознать выбранный коктейль.");
        }
    }

    private List<PartyOption> findCocktails(List<List<Long>> selections) {
        List<Long> ids = selections.stream().flatMap(List::stream).distinct().toList();
        if (ids.isEmpty()) {
            return List.of();
        }
        return cocktailService.getApprovedCocktails(ids).stream()
                .map(this::toPartyCocktail)
                .toList();
    }

    private PartyOption toPartyCocktail(Cocktail cocktail) {
        String image = cocktail.getImage() == null ? null : cocktail.getImage().getImagePath();
        return new PartyOption(cocktail.getId(), cocktail.getName(), image, cocktail.getBaseAlcohol());
    }

    private List<GuestView> createGuestViews(List<String> names, List<List<Long>> selections) {
        Map<Long, PartyOption> cocktailOptions = new LinkedHashMap<>();
        findCocktails(selections).forEach(option -> cocktailOptions.put(option.id(), option));
        List<GuestView> views = new ArrayList<>();
        for (int index = 0; index < names.size(); index++) {
            List<PartyOption> cocktails = selections.get(index).stream()
                    .map(cocktailOptions::get)
                    .filter(java.util.Objects::nonNull)
                    .toList();
            views.add(new GuestView(names.get(index), cocktails));
        }
        return views;
    }

    private List<PartyInventoryItem> parseInventory(List<Long> ids, List<String> amounts, List<String> units) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        if (amounts == null || units == null || ids.size() != amounts.size() || ids.size() != units.size()) {
            throw new IllegalArgumentException("Проверьте список ингредиентов, их количество и единицы.");
        }
        Map<Long, Ingredient> ingredients = new LinkedHashMap<>();
        ingredientRepository.findAllById(ids).forEach(ingredient -> ingredients.put(ingredient.getId(), ingredient));
        if (ingredients.size() != ids.stream().distinct().count()) {
            throw new IllegalArgumentException("Один из выбранных ингредиентов больше недоступен.");
        }

        List<PartyInventoryItem> inventory = new ArrayList<>();
        for (int index = 0; index < ids.size(); index++) {
            BigDecimal amount;
            UnitType unit;
            try {
                amount = new BigDecimal(amounts.get(index).trim().replace(',', '.'));
                unit = UnitType.valueOf(units.get(index));
            } catch (IllegalArgumentException exception) {
                throw new IllegalArgumentException("Укажите корректное количество и единицу для ингредиента.");
            }
            if (amount.signum() < 0) {
                throw new IllegalArgumentException("Количество ингредиента не может быть отрицательным.");
            }
            inventory.add(new PartyInventoryItem(ingredients.get(ids.get(index)).getName(), amount, unit));
        }
        return inventory;
    }

    private List<InventoryView> createInventoryViews(List<Long> ids, List<String> amounts, List<String> units) {
        if (ids == null || amounts == null || units == null || ids.size() != amounts.size()
                || ids.size() != units.size()) {
            return List.of();
        }
        Map<Long, Ingredient> ingredients = new LinkedHashMap<>();
        ingredientRepository.findAllById(ids).forEach(ingredient -> ingredients.put(ingredient.getId(), ingredient));
        List<InventoryView> views = new ArrayList<>();
        for (int index = 0; index < ids.size(); index++) {
            Ingredient ingredient = ingredients.get(ids.get(index));
            if (ingredient != null) {
                views.add(new InventoryView(ingredient.getId(), ingredient.getName(), amounts.get(index), units.get(index)));
            }
        }
        return views;
    }

    public record PartyOption(Long id, String name, String image, String baseAlcohol) {
    }

    public record GuestView(String name, List<PartyOption> cocktails) {
    }

    public record InventoryView(Long id, String name, String amount, String unit) {
    }
}
