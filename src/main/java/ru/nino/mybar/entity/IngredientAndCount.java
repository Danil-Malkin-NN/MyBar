package ru.nino.mybar.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class IngredientAndCount extends IdEntity {

    @OneToOne()
    private Ingredient ingredient;

    private Integer count;

    private UnitType unitType;

}
