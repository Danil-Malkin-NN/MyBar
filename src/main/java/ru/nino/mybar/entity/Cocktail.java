package ru.nino.mybar.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
public class Cocktail extends IdEntity {

    @Nonnull
    private String name;

    private String description = "";

    private int volume = 0;

    private int strength = 0;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<IngredientAndCount> ingredients = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Instrument> instruments = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Step> steps = new ArrayList<>();
}
