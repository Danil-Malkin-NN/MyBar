package ru.nino.mybar.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.Ingredient;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user_entity", schema = "keycloak")
public class User {

    @Id
    private String id;

    private String email;

    private String firstName;
    private String lastName;
    private String username;

    @ManyToMany
    private Set<Cocktail> favoriteCocktail = new HashSet<>();

    @ManyToMany
    private Set<Ingredient> ingredient = new HashSet<>();
}
