package ru.nino.mybar.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Step extends IdEntity {

    private String goal = "";

    private String description = "";

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<IngredientAndCount> usesIngredients = new ArrayList<>();

    @ManyToMany
    private List<Instrument> instruments = new ArrayList<>();

}
