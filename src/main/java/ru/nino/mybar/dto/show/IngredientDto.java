package ru.nino.mybar.dto.show;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IngredientDto extends DTO {

    @NotBlank
    private String name;

    private String description;

}
