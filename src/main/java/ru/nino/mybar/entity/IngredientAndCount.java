package ru.nino.mybar.entity;


import jakarta.persistence.*;

@Entity
public class IngredientAndCount extends IdEntity {

    @OneToOne
    private Ingredient ingredient;

    private Integer count;

    private UnitType unitType;

}
