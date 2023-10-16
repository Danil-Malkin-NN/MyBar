package ru.nino.mybar.mapper.impl;

import org.mapstruct.Mapper;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.mapper.AllMapper;

@Mapper(componentModel = "spring")
public interface CocktailMapperImpl extends AllMapper<CocktailDto, Cocktail> {
}
