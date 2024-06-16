package ru.nino.mybar.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.Ingredient;

import java.util.ArrayList;
import java.util.List;

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
    private List<Cocktail> favoriteCocktail = new ArrayList<>();

    @ManyToMany
    private List<Ingredient> ingredient = new ArrayList<>();
}
