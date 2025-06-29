package ru.nino.mybar.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.Category;
import ru.nino.mybar.repository.NameFinderRepository;

@Repository
public interface CategoryRepository extends NameFinderRepository<Category> {

    @Override
    Category findByName(String s);

    @Override
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
