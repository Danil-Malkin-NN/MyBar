package ru.nino.mybar.dto.show;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.nino.mybar.entity.IngredientAndCount;
import ru.nino.mybar.entity.Instrument;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class StepDto extends DTO {

    @NotBlank
    @Schema(description = "Цель нашего шага", example = "Смешать ингредиенты")
    private String goal;

    @Schema(description = "Описание", example = "Для того что бы смешать ингредиенты нам необходимо взять шейкер добавить туда льда и хорошенько встряхнуть")
    private String description = "";

    @Schema(description = "Используемые в шаге ингредиенты и сколько нужно сейчас")
    private List<IngredientAndCount> usesIngredients = new ArrayList<>();

    @Schema(description = "Используемые в шаге инструменты")
    private List<Instrument> instruments = new ArrayList<>();

}
