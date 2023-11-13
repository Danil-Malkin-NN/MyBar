package ru.nino.mybar.controller.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nino.mybar.controller.CRUDController;
import ru.nino.mybar.dto.show.IngredientDto;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.service.IngredientServiceImpl;

import java.util.List;

@RequestMapping("ingredients")
@RestController
@RequiredArgsConstructor
public class IngredientsControllerImpl implements CRUDController<IngredientDto, Ingredient> {

    private final IngredientServiceImpl service;

    @Override
    @GetMapping("all")
    public List<IngredientDto> getAll() {
        return service.getAll();
    }

    @Override
    @GetMapping("{id}")
    public IngredientDto getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @Override
    @PostMapping("create")
    public IngredientDto create(@RequestBody IngredientDto newObject) {
        return service.create(newObject);
    }

    @Override
    @DeleteMapping("delete")
    public void delete(@RequestParam Integer id) {
        service.delete(id);
    }

    @Override
    @PutMapping("update")
    public IngredientDto update(@RequestBody IngredientDto newData) {
        return service.update(newData);
    }
}
