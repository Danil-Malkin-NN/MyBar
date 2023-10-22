package ru.nino.mybar.entity.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.IdEntity;
import ru.nino.mybar.entity.Ingredient;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class UserInfo extends IdEntity {

    private String email;

    private String firstName;

    private String lastName;

    @OneToOne(cascade = CascadeType.PERSIST)
    private User user;

    @ManyToMany
    private List<Cocktail> favoriteCocktail = new ArrayList<>();

    @ManyToMany
    private List<Ingredient> ingredient = new ArrayList<>();

}
