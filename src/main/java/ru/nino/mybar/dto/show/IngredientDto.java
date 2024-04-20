package ru.nino.mybar.dto.show;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IngredientDto extends DTO {

    @NotBlank
    @Schema(description = "Название ингредиента", example = "Маракуя")
    private String name;

    @Schema(description = "Описания ингредиента", example = "Кисло сладкий тропический фрукт")
    private String description;

}
