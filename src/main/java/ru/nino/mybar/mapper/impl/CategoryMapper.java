package ru.nino.mybar.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nino.mybar.dto.show.CategoryDto;
import ru.nino.mybar.entity.Category;
import ru.nino.mybar.mapper.AllMapper;

@Mapper(uses = IngredientMapperImpl.class)
public interface CategoryMapper extends AllMapper<CategoryDto, Category> {

    @Mapping(source = "ingredients", target = "ingredients", ignore = true)
    CategoryDto toDtoOnlyCategory(Category category);
}
