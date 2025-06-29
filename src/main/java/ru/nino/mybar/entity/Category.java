package ru.nino.mybar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
public class Category extends IdEntity {

    @Column(nullable = false, unique = true)
    public String name;

    @ManyToMany
    public Set<Ingredient> ingredients;

}
