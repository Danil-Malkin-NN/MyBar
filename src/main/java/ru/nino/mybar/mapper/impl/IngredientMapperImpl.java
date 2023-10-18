package ru.nino.mybar.mapper.impl;

import org.mapstruct.Mapper;
import ru.nino.mybar.dto.show.IngredientDto;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.mapper.AllMapper;

@Mapper
public interface IngredientMapperImpl extends AllMapper<IngredientDto, Ingredient> {
}
