package ru.nino.mybar.dto.show;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CocktailDto implements DTO {

    private Integer id;

    private String name;

    private String description = "";

    private int volume = 0;

    private int strength = 0;

    private List<IngredientAndCountDto> ingredients = new ArrayList<>();

    private List<InstrumentsDto> instruments = new ArrayList<>();

    private List<StepsDto> steps = new ArrayList<>();
}
