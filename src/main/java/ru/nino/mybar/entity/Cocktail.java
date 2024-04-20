package ru.nino.mybar.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
public class Cocktail extends IdEntity {

    @Nonnull
    @Column(columnDefinition = "TEXT")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description = "";

    private int volume = 0;

    private int strength = 0;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<IngredientAndCount> ingredients = new ArrayList<>();

    @ManyToMany
    private List<Instrument> instruments = new ArrayList<>();

//    @ManyToMany()
//    private List<Step> steps = new ArrayList<>();
}
