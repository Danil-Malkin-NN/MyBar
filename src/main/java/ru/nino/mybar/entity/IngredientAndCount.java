package ru.nino.mybar.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class IngredientAndCount extends IdEntity {

    @OneToOne
    private Ingredient ingredient;

    private Integer count;

    private UnitType unitType;

}
