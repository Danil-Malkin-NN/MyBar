package ru.nino.mybar.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.nino.mybar.dto.model.CocktailsModel;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.dto.show.CocktailIngredientDto;
import ru.nino.mybar.dto.show.CocktailUserIngredientsDto;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.IdEntity;
import ru.nino.mybar.entity.user.User;
import ru.nino.mybar.mapper.impl.CocktailMapperImpl;
import ru.nino.mybar.repository.impl.CocktailRepositoryImpl;
import ru.nino.mybar.repository.impl.UserRepositoryImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CocktailServiceImpl extends NameFindService<CocktailDto, Cocktail> {

    private final CocktailMapperImpl mapper;
    private final CocktailRepositoryImpl repository;
    private final UserRepositoryImpl userRepository;

    public CocktailServiceImpl(CocktailRepositoryImpl repository, CocktailMapperImpl mapper,
                               UserRepositoryImpl userRepository) {
        super(repository, mapper);
        this.mapper = mapper;
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Page<CocktailIngredientDto> getPageAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toIngredientDto);
    }

    public Page<CocktailsModel> getPageModels(Pageable pageable) {
        Page<Cocktail> all = repository.findAll(pageable);
        return all.map(mapper::toModel);
    }

    public List<CocktailUserIngredientsDto> searchByIngredientList(List<Integer> ingredients) {
        List<CocktailUserIngredientsDto> cocktailUserIngredientsDtos = repository.findByIngredientsList(ingredients)
                .stream()
                .map(mapper::toUserIngredients)
                .toList();

        cocktailUserIngredientsDtos.stream()
                .flatMap(cocktailUserIngredientsDto -> cocktailUserIngredientsDto.getIngredients()
                        .stream())
                .forEach(ingredientAvailableDto -> ingredientAvailableDto.setAvailable(
                        ingredients.contains(ingredientAvailableDto.getId())));

        return cocktailUserIngredientsDtos;
    }

    public Page<CocktailDto> searchByName(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable)
                .map(mapper::toDto);
    }

    public Page<CocktailsModel> getPageWithNameFilterModel(String cocktailName, Pageable pageable, String email) {
        User userInfo = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Информация о пользователе: " + email + " не найдена"));

        Map<Long, List<Cocktail>> collect = userInfo.getFavoriteCocktail()
                .stream()
                .collect(Collectors.groupingBy(IdEntity::getId));

        Page<CocktailsModel> map = repository.findByNameContainingIgnoreCase(cocktailName, pageable)
                .map(mapper::toModel);

        map.stream()
                .filter(cocktailsModel -> collect.containsKey(cocktailsModel.getId()))
                .forEach(cocktailsModel -> cocktailsModel.setFavorite(true));

        return map;
    }

    public Page<CocktailsModel> getPageWithNameFilterModel(String cocktailName, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(cocktailName, pageable)
                .map(mapper::toModel);
    }

    @Override
    protected String getEntityName() {
        return "Cocktail";
    }
}
