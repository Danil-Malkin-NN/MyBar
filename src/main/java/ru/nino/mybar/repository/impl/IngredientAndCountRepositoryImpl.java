package ru.nino.mybar.repository.impl;

import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.IngredientAndCount;
import ru.nino.mybar.repository.DefaultRepository;

@Repository
public interface IngredientAndCountRepositoryImpl extends DefaultRepository<IngredientAndCount> {
}
