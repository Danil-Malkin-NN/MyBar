package ru.nino.mybar.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

@Entity
public class Ingredient extends IdEntity {

    private String name;

}
