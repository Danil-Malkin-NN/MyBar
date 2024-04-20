package ru.nino.mybar.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Step extends IdEntity {

    @Column(columnDefinition = "TEXT")
    private String goal = "";

    @Column(columnDefinition = "TEXT")
    private String description = "";

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<IngredientAndCount> usesIngredients = new ArrayList<>();

    @ManyToMany()
    private List<Instrument> instruments = new ArrayList<>();

}
