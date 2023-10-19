package ru.nino.mybar.mapper.impl;

import org.mapstruct.Mapper;
import ru.nino.mybar.dto.show.IngredientAndCountDto;
import ru.nino.mybar.entity.IngredientAndCount;
import ru.nino.mybar.mapper.AllMapper;

@Mapper
public interface IngredientAndCountMapperImpl extends AllMapper<IngredientAndCountDto, IngredientAndCount> {
}