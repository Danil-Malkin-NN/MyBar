package ru.nino.mybar.dto.model;

import lombok.Data;

@Data
public class CocktailsModel {
    private Long id;
    private String name;
    private String description;
    private String strength;
    private int volume;
    private String baseAlcohol;
    private String ingredients;
    private boolean favorite;
    private String image;
    private boolean containsAlcohol;
    private String submittedBy;
    private double ratingAverage;
    private int ratingCount;
}