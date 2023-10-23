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
import ru.nino.mybar.repository.impl.UserInfoRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserBarService {

    private final UserInfoRepositoryImpl userInfoRepository;

    private final IngredientMapperImpl ingredientMapper;

    private final CocktailRepositoryImpl cocktailRepository;

    private final CocktailMapperImpl cocktailMapper;

    public List<IngredientDto> findAllUserIngredients(String userName) {
        UserInfo userInfo = userInfoRepository.findByUser_NameIgnoreCase(userName)
                .orElseThrow(() -> new RuntimeException("Информация о пользователе не найдена"));

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
}
