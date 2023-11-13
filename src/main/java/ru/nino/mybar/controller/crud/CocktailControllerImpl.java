package ru.nino.mybar.controller.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nino.mybar.controller.CRUDController;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.service.CocktailServiceImpl;

import java.util.List;

@RequestMapping("cocktails")
@RestController
@RequiredArgsConstructor
public class CocktailControllerImpl implements CRUDController<CocktailDto, Cocktail> {

    private final CocktailServiceImpl service;

    @Override
    @GetMapping("all")
    public List<CocktailDto> getAll() {
        return service.getAll();
    }

    @Override
    @GetMapping("{id}")
    public CocktailDto getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @Override
    @PostMapping("create")
    public CocktailDto create(@RequestBody CocktailDto newObject) {
        return service.create(newObject);
    }

    @Override
    @DeleteMapping("delete")
    public void delete(@RequestParam Integer id) {
        service.delete(id);
    }

    @Override
    @PutMapping("update")
    public CocktailDto update(@RequestBody CocktailDto newData) {
        return service.update(newData);
    }
}
