package ru.nino.mybar.entity;


import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Instruments extends IdEntity {

    @Nonnull
    private String name;

    private String description = "";


}
