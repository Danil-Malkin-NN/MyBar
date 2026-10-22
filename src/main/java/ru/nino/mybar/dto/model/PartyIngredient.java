package ru.nino.mybar.dto.model;

import ru.nino.mybar.entity.UnitType;

import java.math.BigDecimal;

public record PartyIngredient(String name, BigDecimal required, BigDecimal available,
                              BigDecimal missing, UnitType unitType) {
    public String unitLabel() {
        return switch (unitType) {
            case MILLILITER -> "мл";
            case GRAM -> "г";
            case PIECES -> "шт";
        };
    }
}
