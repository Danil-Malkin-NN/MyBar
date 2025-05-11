package ru.nino.mybar.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.nino.mybar.dto.show.DTO;
import ru.nino.mybar.dto.show.IngredientDto;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.mapper.impl.IngredientMapperImpl;
import ru.nino.mybar.repository.impl.IngredientRepositoryImpl;
import ru.nino.mybar.repository.impl.UserRepositoryImpl;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl extends NameFindService<IngredientDto, Ingredient> {

    private final UserBarService userBarService;

    public IngredientServiceImpl(IngredientRepositoryImpl repository, IngredientMapperImpl mapper,
                                 UserRepositoryImpl userRepository, UserBarService userBarService) {
        super(repository, mapper);
        this.userBarService = userBarService;
    }

    public Page<IngredientDto> getPageWithUserInfo(Pageable pageable, String email){
        return getPageWithUserInfo(pageable, email, "");
    }
    public Page<IngredientDto> getPageWithUserInfo(Pageable pageable, String email, String ingredientName) {
        Page<IngredientDto> page = getPageWithNameFilter(pageable, ingredientName);
        Map<Long, IngredientDto> availableCocktails = userBarService.findAllUserIngredients(email)
                .stream()
                .collect(Collectors.toMap(DTO::getId, Function.identity()));
        page.forEach(ingredientDto -> {
            if (availableCocktails.containsKey(ingredientDto.getId())) {
                ingredientDto.setAvailable(true);
            }
        });

        return page;
    }

    @Override
    protected String getEntityName() {
        return "Cocktail";
    }

}
