package ru.nino.mybar.dto.model;

public record CocktailsModel(
        Long id,
        String name,
        String description,
        String strength,
        String ingredients
) {

}
