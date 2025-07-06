package ru.nino.mybar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nino.mybar.dto.show.CategoryDto;
import ru.nino.mybar.entity.Category;
import ru.nino.mybar.mapper.impl.CategoryMapper;
import ru.nino.mybar.mapper.impl.IngredientMapperImpl;
import ru.nino.mybar.repository.impl.CategoryRepository;
import ru.nino.mybar.repository.impl.IngredientRepositoryImpl;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final IngredientMapperImpl ingredientMapper;
    private final IngredientRepositoryImpl ingredientRepository;

    private final CategoryMapper categoryMapper;

    public List<CategoryDto> findTopCategory() {
        List<Category> all = categoryRepository.findAll();


        return all.stream()
                .map(categoryMapper::toDtoOnlyCategory)
                .toList();
    }

//    public Category getIngredientsByCategory() {
//        Category category = categoryRepository.findById(1L)
//                .get();
//
//        HashSet<Ingredient> ingredients = new HashSet<>();
//        ingredients.add(ingredientRepository.findByName("Белый ром"));
//        ingredients.add(ingredientRepository.findByName("Российский виски"));
//        ingredients.add(ingredientRepository.findByName("Лондонский сухой джин"));
//        ingredients.add(ingredientRepository.findByName("Водка"));
//        category.ingredients = ingredients;
//
//        return category;
//    }

    public CategoryDto findById(Long categoryId) {
        Category byId = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException());

        return categoryMapper.toDto(byId);
    }
}
