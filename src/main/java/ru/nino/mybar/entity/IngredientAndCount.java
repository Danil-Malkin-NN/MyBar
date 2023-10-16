package ru.nino.mybar.entity;


import jakarta.persistence.*;

@Entity
public class IngredientAndCount extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToOne
    private Ingredient ingredient;

    private Integer count;

    private UnitType unitType;

}
