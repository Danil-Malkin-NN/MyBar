package ru.nino.mybar.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Instrument extends IdEntity {

    private String name;

    @Column(length = 1000)
    private String description = "";


}
