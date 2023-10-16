package ru.nino.mybar.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nino.mybar.controller.CRUDController;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.service.CocktailServiceImpl;

import java.util.List;

@RequestMapping("cocktail")
@RestController
@RequiredArgsConstructor
public class CocktailControllerImpl implements CRUDController<CocktailDto, Cocktail> {

    private final CocktailServiceImpl service;

    @Override
    public List<CocktailDto> getAll() {
        return service.getAll();
    }

    @Override
    public CocktailDto getById(Integer id) {
        return service.getById(id);
    }

    @Override
    public CocktailDto create(CocktailDto newObject) {
        return service.create(newObject);
    }

    @Override
    public void delete(Integer id) {
        service.delete(id);
    }

    @Override
    public CocktailDto update(CocktailDto newData) {
        return service.update(newData);
    }
}
