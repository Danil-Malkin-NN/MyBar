package ru.nino.mybar.dto.model;

import lombok.Data;

@Data
public class CocktailsModel {
    private Long id;
    private String name;
    private String description;
    private String strength;
    private String ingredients;
    private boolean favorite;
    private String image;
}