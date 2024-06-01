package ru.nino.mybar.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class IngredientAndCount extends IdEntity {

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.EAGER)
    private Ingredient ingredient;

    private Integer count;

    private UnitType unitType;

}
