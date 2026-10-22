package ru.nino.mybar.dto.model;

import ru.nino.mybar.entity.UnitType;

import java.math.BigDecimal;

public record PartyInventoryItem(String name, BigDecimal amount, UnitType unitType) {
}
