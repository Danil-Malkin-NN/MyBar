package ru.nino.mybar.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class IngredientAndCount extends IdEntity {

    @ManyToOne(cascade = CascadeType.MERGE)
    private Ingredient ingredient;

    private Integer count;

    private UnitType unitType;

}
