package ru.nino.mybar.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Instrument extends IdEntity {

    @Schema(description = "Название инструмента", example = "Шейкер")
    private String name;

    @Column(length = 1000)
    @Schema(description = "Описание инструмента", example = "Инструмент для смешивания коктейлей")
    private String description = "";


}
