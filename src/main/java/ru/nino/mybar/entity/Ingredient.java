package ru.nino.mybar.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Ingredient extends IdEntity {

    @Column(name = "name", unique = true)
    @Schema(description = "Название ингредиента", example = "водка")
    private String name;

    @Column(length = 3000)
    @Schema(description = "Описание ингредиента", example = "водка")
    private String description;

}
