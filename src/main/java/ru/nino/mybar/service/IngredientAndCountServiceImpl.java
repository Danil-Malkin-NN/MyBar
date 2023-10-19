package ru.nino.mybar.service;

import org.springframework.stereotype.Service;
import ru.nino.mybar.dto.show.IngredientAndCountDto;
import ru.nino.mybar.entity.IngredientAndCount;
import ru.nino.mybar.mapper.impl.IngredientAndCountMapperImpl;
import ru.nino.mybar.repository.impl.IngredientAndCountRepositoryImpl;

@Service
public class IngredientAndCountServiceImpl extends CRUDService<IngredientAndCountDto, IngredientAndCount> {

    public IngredientAndCountServiceImpl(IngredientAndCountRepositoryImpl repository, IngredientAndCountMapperImpl mapper) {
        super(repository, mapper);
    }

    @Override
    protected String getEntityName() {
        return "IngredientAndCount";
    }

}

