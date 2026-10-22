package ru.nino.mybar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nino.mybar.dto.model.PartyIngredient;
import ru.nino.mybar.dto.model.PartyInventoryItem;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.IngredientAndCount;
import ru.nino.mybar.entity.UnitType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartyPlanningService {
    private final CocktailServiceImpl cocktailService;

    public List<PartyIngredient> calculate(int guests, List<Long> cocktailIds, String inventoryText) {
        if (guests < 1 || guests > 250) {
            throw new IllegalArgumentException("Укажите от 1 до 250 гостей.");
        }
        if (cocktailIds == null || cocktailIds.isEmpty()) {
            throw new IllegalArgumentException("Выберите хотя бы один коктейль.");
        }

        List<Cocktail> cocktails = cocktailService.getApprovedCocktails(cocktailIds.stream().distinct().toList());
        if (cocktails.size() != cocktailIds.stream().distinct().count()) {
            throw new IllegalArgumentException("Один из выбранных коктейлей недоступен.");
        }

        Map<Key, BigDecimal> required = new LinkedHashMap<>();
        Map<Key, String> displayNames = new LinkedHashMap<>();
        for (Cocktail cocktail : cocktails) {
            for (IngredientAndCount ingredient : cocktail.getIngredients()) {
                if (ingredient.getCount() == null || ingredient.getCount() <= 0
                        || ingredient.getIngredient() == null || ingredient.getUnitType() == null) {
                    throw new IllegalStateException("В рецепте «" + cocktail.getName()
                            + "» есть ингредиент без корректного количества или единицы измерения.");
                }
                Key key = new Key(normalize(ingredient.getIngredient().getName()), ingredient.getUnitType());
                required.merge(key, BigDecimal.valueOf((long) ingredient.getCount() * guests), BigDecimal::add);
                displayNames.putIfAbsent(key, ingredient.getIngredient().getName());
            }
        }

        Map<Key, BigDecimal> available = parseInventory(inventoryText);
        List<PartyIngredient> result = new ArrayList<>();
        required.forEach((key, total) -> {
            BigDecimal have = available.getOrDefault(key, BigDecimal.ZERO);
            BigDecimal missing = total.subtract(have).max(BigDecimal.ZERO);
            result.add(new PartyIngredient(displayNames.get(key), clean(total), clean(have),
                    clean(missing), key.unitType()));
        });
        return result;
    }

    public List<PartyIngredient> calculateSelections(List<List<Long>> guestCocktailIds,
                                                     List<PartyInventoryItem> inventoryItems) {
        if (guestCocktailIds == null || guestCocktailIds.isEmpty() || guestCocktailIds.size() > 20) {
            throw new IllegalArgumentException("Укажите от 1 до 20 гостей.");
        }
        List<Long> selectedIds = guestCocktailIds.stream()
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .toList();
        if (selectedIds.isEmpty()) {
            throw new IllegalArgumentException("Добавьте хотя бы один коктейль гостям.");
        }

        List<Long> uniqueIds = selectedIds.stream().distinct().toList();
        List<Cocktail> cocktails = cocktailService.getApprovedCocktails(uniqueIds);
        if (cocktails.size() != uniqueIds.size()) {
            throw new IllegalArgumentException("Один из выбранных коктейлей недоступен.");
        }
        Map<Long, Cocktail> cocktailsById = cocktails.stream()
                .collect(Collectors.toMap(Cocktail::getId, cocktail -> cocktail));

        Map<Key, BigDecimal> required = new LinkedHashMap<>();
        Map<Key, String> displayNames = new LinkedHashMap<>();
        for (List<Long> guestIds : guestCocktailIds) {
            for (Long cocktailId : guestIds) {
                Cocktail cocktail = cocktailsById.get(cocktailId);
                if (cocktail == null) {
                    throw new IllegalArgumentException("Один из выбранных коктейлей недоступен.");
                }
                for (IngredientAndCount ingredient : cocktail.getIngredients()) {
                    if (ingredient.getCount() == null || ingredient.getCount() <= 0
                            || ingredient.getIngredient() == null || ingredient.getUnitType() == null) {
                        throw new IllegalStateException("В рецепте «" + cocktail.getName()
                                + "» есть ингредиент без корректного количества или единицы измерения.");
                    }
                    Key key = new Key(normalize(ingredient.getIngredient().getName()), ingredient.getUnitType());
                    required.merge(key, BigDecimal.valueOf(ingredient.getCount()), BigDecimal::add);
                    displayNames.putIfAbsent(key, ingredient.getIngredient().getName());
                }
            }
        }

        Map<Key, BigDecimal> available = new LinkedHashMap<>();
        if (inventoryItems != null) {
            for (PartyInventoryItem item : inventoryItems) {
                if (item == null || item.name() == null || item.name().isBlank()
                        || item.amount() == null || item.amount().signum() < 0 || item.unitType() == null) {
                    throw new IllegalArgumentException("Проверьте количество и единицу выбранных ингредиентов.");
                }
                Key key = new Key(normalize(item.name()), item.unitType());
                available.merge(key, item.amount(), BigDecimal::add);
            }
        }

        List<PartyIngredient> result = new ArrayList<>();
        required.forEach((key, total) -> {
            BigDecimal have = available.getOrDefault(key, BigDecimal.ZERO);
            BigDecimal missing = total.subtract(have).max(BigDecimal.ZERO);
            result.add(new PartyIngredient(displayNames.get(key), clean(total), clean(have),
                    clean(missing), key.unitType()));
        });
        return result;
    }

    public List<PartyRecommendation> recommendCocktails(List<Long> availableIngredientIds) {
        return recommendCocktails(availableIngredientIds, 3);
    }

    public List<PartyRecommendation> recommendCocktails(List<Long> availableIngredientIds, int maxMissingIngredients) {
        if (availableIngredientIds == null || availableIngredientIds.isEmpty()) {
            return List.of();
        }
        if (maxMissingIngredients < 0) {
            throw new IllegalArgumentException("Количество недостающих ингредиентов не может быть отрицательным.");
        }
        Set<Long> available = Set.copyOf(availableIngredientIds);
        return cocktailService.getApprovedCocktails().stream()
                .map(cocktail -> {
                    List<String> missing = cocktail.getIngredients().stream()
                            .filter(item -> item.getIngredient() != null)
                            .filter(item -> !available.contains(item.getIngredient().getId()))
                            .map(item -> item.getIngredient().getName())
                            .distinct()
                            .toList();
                    long matched = cocktail.getIngredients().stream()
                            .filter(item -> item.getIngredient() != null)
                            .map(item -> item.getIngredient().getId())
                            .filter(available::contains)
                            .distinct()
                            .count();
                    return new PartyRecommendation(cocktail.getId(), cocktail.getName(),
                            cocktail.getStrength(), cocktail.getVolume(), (int) matched, missing,
                            cocktail.getImage() == null ? null : cocktail.getImage().getImagePath());
                })
                .filter(recommendation -> recommendation.matchedIngredients() > 0
                        && recommendation.missingIngredients().size() <= maxMissingIngredients)
                .sorted(java.util.Comparator.comparingInt(
                                (PartyRecommendation recommendation) -> recommendation.missingIngredients().size())
                        .thenComparing(PartyRecommendation::name))
                .limit(30)
                .toList();
    }

    private Map<Key, BigDecimal> parseInventory(String inventoryText) {
        Map<Key, BigDecimal> inventory = new LinkedHashMap<>();
        if (inventoryText == null || inventoryText.isBlank()) {
            return inventory;
        }
        String[] lines = inventoryText.split("\\R");
        for (int index = 0; index < lines.length; index++) {
            String line = lines[index].trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\|");
            if (parts.length != 3 || parts[0].isBlank()) {
                throw new IllegalArgumentException("Строка " + (index + 1)
                        + ": используйте формат «ингредиент | количество | единица».");
            }
            BigDecimal amount;
            try {
                amount = new BigDecimal(parts[1].trim().replace(',', '.'));
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Строка " + (index + 1) + ": количество должно быть числом.");
            }
            if (amount.signum() < 0) {
                throw new IllegalArgumentException("Строка " + (index + 1) + ": количество не может быть отрицательным.");
            }
            UnitType unitType = parseUnit(parts[2].trim(), index + 1);
            Key key = new Key(normalize(parts[0]), unitType);
            inventory.merge(key, amount, BigDecimal::add);
        }
        return inventory;
    }

    private UnitType parseUnit(String input, int line) {
        return switch (input.trim().toLowerCase(Locale.ROOT)) {
            case "ml", "мл", "milliliter", "milliliters", "millilitre", "millilitres" -> UnitType.MILLILITER;
            case "g", "гр", "г", "gram", "grams" -> UnitType.GRAM;
            case "pc", "pcs", "шт", "piece", "pieces" -> UnitType.PIECES;
            default -> throw new IllegalArgumentException("Строка " + line
                    + ": единица должна быть мл, г или шт.");
        };
    }

    private BigDecimal clean(BigDecimal value) {
        return new BigDecimal(value.stripTrailingZeros().toPlainString());
    }

    private String normalize(String value) {
        return value.trim().toLowerCase(Locale.ROOT);
    }

    private record Key(String ingredient, UnitType unitType) {
    }

    public record PartyRecommendation(Long id, String name, int strength, int volume,
                                      int matchedIngredients, List<String> missingIngredients, String image) {
    }
}
