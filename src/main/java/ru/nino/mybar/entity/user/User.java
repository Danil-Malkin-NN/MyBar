package ru.nino.mybar.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.IdEntity;
import ru.nino.mybar.entity.Image;
import ru.nino.mybar.entity.Ingredient;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user_data")
public class User extends IdEntity {

    @Column(nullable = false)
    private String email;

    private String firstName;
    private String lastName;
    private String username;

    @ManyToMany
    private Set<Cocktail> favoriteCocktail = new HashSet<>();

    @ManyToMany
    private Set<Ingredient> ingredient = new HashSet<>();

    @OneToOne
    private Image image;
}
