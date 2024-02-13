package ru.nino.mybar.dto.show;

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
    private String goal;

    private String description = "";

    private List<IngredientAndCount> usesIngredients = new ArrayList<>();

    private List<Instrument> instruments = new ArrayList<>();

}
