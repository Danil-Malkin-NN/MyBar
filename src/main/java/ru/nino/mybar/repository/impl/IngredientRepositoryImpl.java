package ru.nino.mybar.repository.impl;

import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.repository.DefaultRepository;

@Repository
public interface IngredientRepositoryImpl extends DefaultRepository<Ingredient> {
}
