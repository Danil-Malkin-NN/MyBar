package ru.nino.mybar.controller.crud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.nino.mybar.controller.CRUDController;
import ru.nino.mybar.dto.show.IngredientAndCountDto;
import ru.nino.mybar.entity.IngredientAndCount;
import ru.nino.mybar.service.IngredientAndCountServiceImpl;

import java.util.List;

@Tag(name = """
        Контроллер для редактирования информации о ингредиентах и их количестве.
        """,
    description = """
            Данная информация требуется в разрезе конкретного шага или рецепта. Например 50 Мл Водки
            """)
@RequestMapping("ingredientsAndCount")
@RestController
@RequiredArgsConstructor
public class IngredientAndCountControllerImpl implements CRUDController<IngredientAndCountDto, IngredientAndCount> {

    private final IngredientAndCountServiceImpl service;

    @Override
    @GetMapping("all")
    @Operation(description = "Отдаёт список ингредиентов и их количества")
    public List<IngredientAndCountDto> getAll() {
        return service.getAll();
    }

    @Override
    @GetMapping
    public Page<IngredientAndCountDto> getPage(Pageable pageable) {
        return service.getPage(pageable);
    }

    @Override
    @GetMapping("{id}")
    @Operation(description = "Отдаёт список ингредиентов и их количества")
    public IngredientAndCountDto getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @Override
    @PostMapping("create")
    @Operation(description = "Отдаёт список ингредиентов и их количества")
    public IngredientAndCountDto create(@RequestBody IngredientAndCountDto newObject) {
        return service.create(newObject);
    }

    @Override
    @DeleteMapping("delete")
    @Operation(description = "Удаляет по идентификатору ингредиент и его количество")
    public void delete(@RequestParam Integer id) {
        service.delete(id);
    }

    @Override
    @PutMapping("update")
    @Operation(description = "Обновляет ингредиент и его количество")
    public IngredientAndCountDto update(@RequestBody IngredientAndCountDto newData) {
        return service.update(newData);
    }
}
