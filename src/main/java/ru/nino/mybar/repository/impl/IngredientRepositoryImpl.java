package ru.nino.mybar.repository.impl;

import org.jacoco.core.internal.analysis.filter.IFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.repository.NameFinderRepository;

import java.util.List;

@Repository
public interface IngredientRepositoryImpl extends NameFinderRepository<Ingredient> {

    List<Ingredient> findTop5ByNameLikeIgnoreCase(String name);

    @Override
    Ingredient findByName(String s);

    @Override
    Page<Ingredient> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
