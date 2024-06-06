package ru.nino.mybar.mapper.impl;

import org.mapstruct.Mapper;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.dto.show.CocktailIngredientDto;
import ru.nino.mybar.dto.show.CocktailUserIngredientsDto;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.mapper.AllMapper;

@Mapper(componentModel = "spring", uses = {IngredientAndCountMapperImpl.class,
        InstrumentsMapperImpl.class,
        StepsMapperImpl.class,
        IngredientMapperImpl.class
})
public interface CocktailMapperImpl extends AllMapper<CocktailDto, Cocktail> {

    CocktailIngredientDto toIngredientDto(Cocktail cocktail);

    CocktailUserIngredientsDto toUserIngredients(Cocktail cocktail);
}
