package ru.nino.mybar.dto.show;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IngredientDto extends DTO {

    private String name;

    private String description;

}
