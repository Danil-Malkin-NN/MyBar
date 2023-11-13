package ru.nino.mybar.controller.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nino.mybar.controller.CRUDController;
import ru.nino.mybar.dto.show.IngredientAndCountDto;
import ru.nino.mybar.entity.IngredientAndCount;
import ru.nino.mybar.service.IngredientAndCountServiceImpl;

import java.util.List;

@RequestMapping("ingredientsAndCount")
@RestController
@RequiredArgsConstructor
public class IngredientAndCountControllerImpl implements CRUDController<IngredientAndCountDto, IngredientAndCount> {

    private final IngredientAndCountServiceImpl service;

    @Override
    @GetMapping("all")
    public List<IngredientAndCountDto> getAll() {
        return service.getAll();
    }

    @Override
    @GetMapping("{id}")
    public IngredientAndCountDto getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @Override
    @PostMapping("create")
    public IngredientAndCountDto create(@RequestBody IngredientAndCountDto newObject) {
        return service.create(newObject);
    }

    @Override
    @DeleteMapping("delete")
    public void delete(@RequestParam Integer id) {
        service.delete(id);
    }

    @Override
    @PutMapping("update")
    public IngredientAndCountDto update(@RequestBody IngredientAndCountDto newData) {
        return service.update(newData);
    }
}
