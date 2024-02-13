package ru.nino.mybar.dto.show;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CocktailDto extends DTO {

    @NotBlank
    @Schema(description = "Название коктейля", example = "Марго дайкири")
    private String name;

    @Schema(description = "Описание напитка", example = "Вкусный коктейль с фруктово ягодой ноткой")
    private String description = "";

    @Schema(description = "Объём коктейля в миллилитрах", example = "130")
    private int volume = 0;

    @Schema(description = "Крепкость напитка в градусах", example = "13")
    private int strength = 0;

    @Schema(description = "Список ингредиентов и их количество для коктейля ")
    private List<IngredientAndCountDto> ingredients = new ArrayList<>();

    @Schema(description = "Список инструментов и их количество для коктейля ")
    private List<InstrumentDto> instruments = new ArrayList<>();

    @Schema(description = "Список шагов и их количество для коктейля ")
    private List<StepDto> steps = new ArrayList<>();
}
