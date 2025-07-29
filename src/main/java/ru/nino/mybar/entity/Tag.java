package ru.nino.mybar.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tag extends IdEntity {

    private String name;


}
