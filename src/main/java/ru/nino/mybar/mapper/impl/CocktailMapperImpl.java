package ru.nino.mybar.mapper.impl;

import org.mapstruct.Mapper;
import ru.nino.mybar.dto.model.CocktailsModel;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.dto.show.CocktailIngredientDto;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.entity.IngredientAndCount;
import ru.nino.mybar.mapper.AllMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IngredientAndCountMapperImpl.class,
        InstrumentsMapperImpl.class,
        StepsMapperImpl.class,
        IngredientMapperImpl.class
})
public interface CocktailMapperImpl extends AllMapper<CocktailDto, Cocktail> {

    CocktailIngredientDto toIngredientDto(Cocktail cocktail);

    CocktailsModel toModel(Cocktail cocktails);

    List<CocktailsModel> toModelList(List<Cocktail> cocktails);

    default String toIngredientsString(List<IngredientAndCount> ingredientAndCounts){
        List<String> list = ingredientAndCounts.stream()
                .map(IngredientAndCount::getIngredient)
                .map(Ingredient::getName)
                .toList();

        return String.join(", ", list);
    }
}
