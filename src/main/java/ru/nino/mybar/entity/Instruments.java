package ru.nino.mybar.entity;


import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;

@Entity
public class Instruments extends IdEntity {

    @Nonnull
    private String name;

    private String description = "";


}
