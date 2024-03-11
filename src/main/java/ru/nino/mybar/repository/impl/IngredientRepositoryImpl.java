package ru.nino.mybar.repository.impl;

import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.repository.NameFinderRepository;

import java.util.List;

@Repository
public interface IngredientRepositoryImpl extends NameFinderRepository<Ingredient> {

    List<Ingredient> findByNameLikeIgnoreCase(String name);

}
