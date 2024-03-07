package ru.nino.mybar.dto.show;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstrumentDto extends DTO {

    @NotBlank
    @Schema(description = "Название инструмента", example = "Шейкер")
    private String name;

    @Schema(description = "Описание инструмента", example = "Металлическая ёмкость для смешения жидкостей. Шейкер обычно трясут вместе с льдом")
    private String description = "";

}
