package ru.nino.mybar.controller.crud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.nino.mybar.controller.CRUDController;
import ru.nino.mybar.dto.show.IngredientDto;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.service.IngredientServiceImpl;

import java.util.List;

@Tag(name = """
        Контроллер для ингредиентов
        """)
@RequestMapping("ingredients")
@RestController
@RequiredArgsConstructor
public class IngredientsControllerImpl implements CRUDController<IngredientDto, Ingredient> {

    private final IngredientServiceImpl service;

    @GetMapping("search")
    public List<IngredientDto> searchByName(String name) {
        return service.searchByName(name);
    }

    @Override
    @GetMapping("all")
    @Operation(description = "Отдаёт список ингредиентов")
    public List<IngredientDto> getAll() {
        return service.getAll();
    }

    @Override
    @GetMapping
    public Page<IngredientDto> getPage(Pageable pageable) {
        return service.getPage(pageable);
    }

    @Override
    @GetMapping("{id}")
    @Operation(description = "Отдаёт ингредиент по идентификатору")
    public IngredientDto getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @Override
    @PostMapping("create")
    @Operation(description = "Создаёт ингредиент")
    public IngredientDto create(@RequestBody IngredientDto newObject) {
        return service.create(newObject);
    }

    @Override
    @DeleteMapping("delete")
    @Operation(description = "Удаляет ингредиент по идентификатору")
    public void delete(@RequestParam Integer id) {
        service.delete(id);
    }

    @Override
    @PutMapping("update")
    @Operation(description = "Обновляет ингредиент")
    public IngredientDto update(@RequestBody IngredientDto newData) {
        return service.update(newData);
    }
}
