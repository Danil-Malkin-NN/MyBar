package ru.nino.mybar.repository.impl;

import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.repository.DefaultRepository;

@Repository
public interface CocktailRepositoryImpl extends DefaultRepository<Cocktail> {
}
