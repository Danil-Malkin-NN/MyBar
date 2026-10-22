package ru.nino.mybar.dto.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CocktailSubmissionForm {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String baseAlcohol;

    @Min(0)
    @Max(100)
    private int strength;

    @Min(1)
    @Max(2000)
    private int volume;

    @NotBlank
    private String ingredients;

    @NotBlank
    private String steps;
}
