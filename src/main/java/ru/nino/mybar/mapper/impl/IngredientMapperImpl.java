package ru.nino.mybar.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nino.mybar.dto.show.IngredientDto;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.mapper.AllMapper;

@Mapper(componentModel = "spring")
public interface IngredientMapperImpl extends AllMapper<IngredientDto, Ingredient> {

    @Override
    @Mapping(source = "name", target = "name")
    IngredientDto toDto(Ingredient ingredient);
}
