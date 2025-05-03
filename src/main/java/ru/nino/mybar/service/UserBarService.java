package ru.nino.mybar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nino.mybar.dto.show.CocktailUserIngredientsDto;
import ru.nino.mybar.dto.show.IngredientDto;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.IdEntity;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.entity.user.User;
import ru.nino.mybar.mapper.impl.CocktailMapperImpl;
import ru.nino.mybar.mapper.impl.IngredientMapperImpl;
import ru.nino.mybar.repository.impl.CocktailRepositoryImpl;
import ru.nino.mybar.repository.impl.IngredientRepositoryImpl;
import ru.nino.mybar.repository.impl.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserBarService {

    private final UserRepositoryImpl userRepository;

    private final IngredientMapperImpl ingredientMapper;

    private final CocktailRepositoryImpl cocktailRepository;

    private final CocktailMapperImpl cocktailMapper;

    private final IngredientRepositoryImpl ingredientRepository;

    public List<IngredientDto> findAllUserIngredients(String userName) {
        User userInfo = userRepository.findByEmail(userName)
                .orElseThrow(() -> new RuntimeException("Информация о пользователе: " + userName + " не найдена"));

        List<Ingredient> ingredient = userInfo.getIngredient();
        return ingredient.stream()
                .map(ingredientMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CocktailUserIngredientsDto> getAvailableCocktails(String userName) {

        List<Cocktail> cocktails = cocktailRepository.getAvailableCocktails(userName);

        var userIngredients = userRepository.findByEmail(userName)
                .map(User::getIngredient)
                .orElseGet(ArrayList::new)
                .stream()
                .map(IdEntity::getId)
                .collect(Collectors.toSet());


        var cocktailDtos = cocktails.stream()
                .map(cocktailMapper::toUserIngredients)
                .collect(Collectors.toList());


        cocktailDtos.stream()
                .flatMap(cocktailUserIngredientsDto -> cocktailUserIngredientsDto.getIngredients()
                        .stream())
                .forEach(ingredientAvailableDto -> ingredientAvailableDto.setAvailable(userIngredients.contains(ingredientAvailableDto.getId())));


        return cocktailDtos;
    }

    public List<IngredientDto> addIngredient(String email, Long ingredientsId) {
        User userInfo = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Информация о пользователе: " + email + " не найдена"));

        Ingredient ingredient = ingredientRepository.findById(ingredientsId)
                .orElseThrow(() -> new RuntimeException("Ингредиент не найден"));

        List<Ingredient> userIngredients = userInfo.getIngredient();
        userIngredients.add(ingredient);

        userRepository.save(userInfo);

        return userIngredients.stream()
                .map(ingredientMapper::toDto)
                .toList();
    }

    public List<IngredientDto> deleteIngredientsFromMyBar(String userName, Long ingredientsId) {
        User userInfo = userRepository.findByEmail(userName)
                .orElseThrow(() -> new RuntimeException("Информация о пользователе: " + userName + " не найдена"));

        List<Ingredient> userIngredients = userInfo.getIngredient();

        userInfo.setIngredient(userIngredients.stream()
                .filter(ingr -> !Objects.equals(ingr.getId(), ingredientsId))
                .collect(Collectors.toList()));

        userRepository.save(userInfo);

        return userInfo.getIngredient()
                .stream()
                .map(ingredientMapper::toDto)
                .toList();
    }
}
