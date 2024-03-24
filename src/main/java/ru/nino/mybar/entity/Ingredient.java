package ru.nino.mybar.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Ingredient extends IdEntity {

    @Column(name = "name", unique = true, columnDefinition = "TEXT")
    @Schema(description = "Название ингредиента", example = "водка")
    private String name;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Описание ингредиента", example = "водка")
    private String description;

}
