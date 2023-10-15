package ru.nino.mybar.dto.show;


import jakarta.annotation.Nonnull;
import lombok.Data;

@Data
public class InstrumentsDto implements DTO {

    private Integer id;

    @Nonnull
    private String name;

    private String description = "";

}
