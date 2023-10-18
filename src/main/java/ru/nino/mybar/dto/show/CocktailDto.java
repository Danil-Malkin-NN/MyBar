package ru.nino.mybar.dto.show;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CocktailDto extends DTO {

    private String name;

    private String description = "";

    private int volume = 0;

    private int strength = 0;

    private List<IngredientAndCountDto> ingredients = new ArrayList<>();

    private List<InstrumentDto> instruments = new ArrayList<>();

    private List<StepsDto> steps = new ArrayList<>();
}
