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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InstrumentDto extends DTO {

    @NotBlank
    @Schema(description = "Название инструмента", example = "Шейкер")
    private String name;

    @Builder.Default
    @Schema(description = "Описание инструмента", example = "Металлическая ёмкость для смешения жидкостей. Шейкер обычно трясут вместе с льдом")
    private String description = "";


}
