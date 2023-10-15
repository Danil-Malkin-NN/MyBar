package ru.nino.mybar.dto.show;


import lombok.Data;
import ru.nino.mybar.entity.UnitType;

@Data
public class IngredientAndCountDto implements DTO {

    private Integer id;

    private IngredientDto ingredient;

    private Integer count;

    private UnitType unitType;

}
