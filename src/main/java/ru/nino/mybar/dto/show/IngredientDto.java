package ru.nino.mybar.dto.show;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IngredientDto extends DTO {

    @NotBlank
    @Schema(description = "Название ингредиента", example = "Маракуя")
    private String name;

    @Schema(description = "Описания ингредиента", example = "Кисло сладкий тропический фрукт")
    private String description;

}
