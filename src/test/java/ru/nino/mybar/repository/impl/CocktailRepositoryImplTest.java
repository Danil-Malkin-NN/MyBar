package ru.nino.mybar.repository.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.nino.mybar.config.PostgresDbForTest;

@SpringBootTest
@ActiveProfiles("test")
class CocktailRepositoryImplTest extends PostgresDbForTest {

    @Autowired
    CocktailRepositoryImpl repository;

    @Test
    @DisplayName("У пользователя есть коктейли")
    public void availableCocktail() {

        var testUserName = repository.getAvailableCocktails("TestUserName");


        Assertions.assertTrue(testUserName.stream()
                .anyMatch(cocktail -> "Гимлет".equals(cocktail.getName())));
    }

    @Test
    @DisplayName("Пользователя не существует, доступных коктейлей не должно быть.")
    public void noAvailableCocktail() {

        var testUserName = repository.getAvailableCocktails("NotExistUser");

        Assertions.assertTrue(testUserName.isEmpty());
    }

}