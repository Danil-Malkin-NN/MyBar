package ru.nino.mybar.dto.show;


import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstrumentsDto extends DTO {

    @Nonnull
    private String name;

    private String description = "";

}
