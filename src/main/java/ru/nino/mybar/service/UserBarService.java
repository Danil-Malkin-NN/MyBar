package ru.nino.mybar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.dto.show.IngredientDto;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.entity.user.UserInfo;
import ru.nino.mybar.mapper.impl.CocktailMapperImpl;
import ru.nino.mybar.mapper.impl.IngredientMapperImpl;
import ru.nino.mybar.repository.impl.CocktailRepositoryImpl;
import ru.nino.mybar.repository.impl.IngredientRepositoryImpl;
import ru.nino.mybar.repository.impl.UserInfoRepositoryImpl;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserBarService {

    public static final Supplier<RuntimeException> USER_NOT_FOUND = () -> new RuntimeException("Информация о пользователе не найдена");
    private final UserInfoRepositoryImpl userInfoRepository;

    private final IngredientMapperImpl ingredientMapper;

    private final CocktailRepositoryImpl cocktailRepository;

    private final CocktailMapperImpl cocktailMapper;

    private final IngredientRepositoryImpl ingredientRepository;

    public List<IngredientDto> findAllUserIngredients(String userName) {
        UserInfo userInfo = userInfoRepository.findByUser_NameIgnoreCase(userName)
                .orElseThrow(USER_NOT_FOUND);

        List<Ingredient> ingredient = userInfo.getIngredient();
        return ingredient.stream()
                .map(ingredientMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CocktailDto> getAvailableCocktails(String name) {

        List<Cocktail> cocktails = cocktailRepository.getAvailableCocktails(name);
        return cocktails.stream()
                .map(cocktailMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<IngredientDto> addIngredient(String userName, Integer ingredientsId) {
        UserInfo userInfo = userInfoRepository.findByUser_NameIgnoreCase(userName)
                .orElseThrow(USER_NOT_FOUND);
        Ingredient ingredient = ingredientRepository.findById(ingredientsId)
                .orElseThrow(() -> new RuntimeException("Ингредиент не найден"));

        List<Ingredient> userIngredients = userInfo.getIngredient();
        userIngredients.add(ingredient);

        userInfoRepository.save(userInfo);

        return userIngredients.stream()
                .map(ingredientMapper::toDto)
                .toList();
    }

    public List<IngredientDto> deleteIngredientsFromMyBar(String userName, Integer ingredientsId) {
        UserInfo userInfo = userInfoRepository.findByUser_NameIgnoreCase(userName)
                .orElseThrow(USER_NOT_FOUND);

        List<Ingredient> userIngredients = userInfo.getIngredient();

        userInfo.setIngredient(userIngredients.stream()
                .filter(ingr -> !Objects.equals(ingr.getId(), ingredientsId))
                .collect(Collectors.toList()));

        userInfoRepository.save(userInfo);

        return userInfo.getIngredient()
                .stream()
                .map(ingredientMapper::toDto)
                .toList();
    }
}
