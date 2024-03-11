package ru.nino.mybar.service;

import org.springframework.stereotype.Service;
import ru.nino.mybar.dto.show.IngredientDto;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.mapper.impl.IngredientMapperImpl;
import ru.nino.mybar.repository.impl.IngredientRepositoryImpl;

@Service
public class IngredientServiceImpl extends NameFindService<IngredientDto, Ingredient> {

    public IngredientServiceImpl(IngredientRepositoryImpl repository, IngredientMapperImpl mapper) {
        super(repository, mapper);
    }

    @Override
    protected String getEntityName() {
        return "Cocktail";
    }

}
