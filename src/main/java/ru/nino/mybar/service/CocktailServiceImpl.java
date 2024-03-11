package ru.nino.mybar.service;

import org.springframework.stereotype.Service;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.mapper.impl.CocktailMapperImpl;
import ru.nino.mybar.repository.impl.CocktailRepositoryImpl;

@Service
public class CocktailServiceImpl extends NameFindService<CocktailDto, Cocktail> {


    public CocktailServiceImpl(CocktailRepositoryImpl repository, CocktailMapperImpl mapper) {
        super(repository, mapper);
    }

    @Override
    protected String getEntityName() {
        return "Cocktail";
    }
}
