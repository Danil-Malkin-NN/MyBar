package ru.nino.mybar.dto.show;


import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.nino.mybar.entity.UnitType;

@Data
@EqualsAndHashCode(callSuper = true)
public class IngredientAndCountDto extends DTO {

    private IngredientDto ingredient;

    private Integer count;

    private UnitType unitType;

}
