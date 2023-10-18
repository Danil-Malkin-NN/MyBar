package ru.nino.mybar.entity;


import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Instrument extends IdEntity {

    private String name;

    private String description = "";


}
