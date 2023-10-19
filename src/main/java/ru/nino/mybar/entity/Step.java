package ru.nino.mybar.entity;

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

    @ManyToMany
    private List<IngredientAndCount> usesIngredients = new ArrayList<>();

}
