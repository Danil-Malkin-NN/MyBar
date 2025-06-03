package ru.nino.mybar.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nino.mybar.dto.show.IngredientAvailableDto;
import ru.nino.mybar.dto.show.IngredientDto;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.entity.IngredientAndCount;
import ru.nino.mybar.mapper.AllMapper;

@Mapper(componentModel = "spring", uses = {ImageMapper.class,})
public interface IngredientMapperImpl extends AllMapper<IngredientDto, Ingredient> {

    @Override
    IngredientDto toDto(Ingredient ingredient);

    @Mapping(target = "id", source = "ingredient.id")
    @Mapping(target = "name", source = "ingredient.name")
    @Mapping(target = "description", source = "ingredient.description")
    IngredientAvailableDto toAvailableDto(IngredientAndCount ingredient);
}
