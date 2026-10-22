package ru.nino.mybar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cocktail_id", "user_identifier"}))
public class CocktailRating extends IdEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cocktail cocktail;

    @Column(name = "user_identifier", nullable = false, length = 255)
    private String userIdentifier;

    @Column(nullable = false)
    private int score;
}
