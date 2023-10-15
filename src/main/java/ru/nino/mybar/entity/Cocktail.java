package ru.nino.mybar.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Cocktail implements Enti {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Nonnull
    private String name;

    private String description = "";

    private int volume = 0;

    private int strength = 0;

    @ManyToMany
    private List<IngredientAndCount> ingredients = new ArrayList<>();

    @ManyToMany
    private List<Instruments> instruments = new ArrayList<>();

    @ManyToMany
    private List<Steps> steps = new ArrayList<>();
}
