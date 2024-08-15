package ru.nino.mybar.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.dto.show.CocktailIngredientDto;
import ru.nino.mybar.dto.show.CocktailUserIngredientsDto;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.mapper.impl.CocktailMapperImpl;
import ru.nino.mybar.repository.impl.CocktailRepositoryImpl;

import java.util.List;

@Service
public class CocktailServiceImpl extends NameFindService<CocktailDto, Cocktail> {

    private final CocktailMapperImpl mapper;
    private final CocktailRepositoryImpl repository;

    public CocktailServiceImpl(CocktailRepositoryImpl repository, CocktailMapperImpl mapper) {
        super(repository, mapper);
        this.mapper = mapper;
        this.repository = repository;
    }

    public List<CocktailUserIngredientsDto> searchByIngredientList(List<Integer> ingredients) {
        repository.findByIngredientsList(ingredients);

        return null;
    }

    @Override
    protected String getEntityName() {
        return "Cocktail";
    }

    public Page<CocktailIngredientDto> getPageAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toIngredientDto);
    }
}
