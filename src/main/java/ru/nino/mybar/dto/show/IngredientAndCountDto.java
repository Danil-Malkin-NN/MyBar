package ru.nino.mybar.dto.show;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.nino.mybar.entity.UnitType;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IngredientAndCountDto extends DTO {

    @Schema(description = "Какой ингридиент используется в шаге рецепта", example = "")
    private IngredientDto ingredient;

    @Schema(description = "Количество ингридиента", example = "100")
    private Integer count;

    @Schema(description = "Единицы измерения", example = "GRAM")
    private UnitType unitType;

}
