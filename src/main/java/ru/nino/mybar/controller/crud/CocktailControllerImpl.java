package ru.nino.mybar.controller.crud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nino.mybar.controller.CRUDController;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.service.CocktailServiceImpl;

import java.util.List;

@Tag(name = "Контроллер для редактирования информации о коктейлях")
@RequestMapping("cocktails")
@RestController
@RequiredArgsConstructor
public class CocktailControllerImpl implements CRUDController<CocktailDto, Cocktail> {

    private final CocktailServiceImpl service;

    @Override
    @Operation(description = "Отображает список всех доступных коктейлей")
    @GetMapping("all")
    public List<CocktailDto> getAll() {
        return service.getAll();
    }

    @Override
    @Operation(description = "Отображает коктейль по его идентификатору")
    @GetMapping("{id}")
    public CocktailDto getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @Override
    @Operation(description = "Создаёт коктейль из DTO")
    @PostMapping("create")
    public CocktailDto create(@RequestBody CocktailDto newObject) {
        return service.create(newObject);
    }

    @Override
    @Operation(description = "Удаляет коктейль по идентификатору")
    @DeleteMapping("delete")
    public void delete(@RequestParam Integer id) {
        service.delete(id);
    }

    @Override
    @Operation(description = "Обновляет существующий коктейль")
    @PutMapping("update")
    public CocktailDto update(@RequestBody CocktailDto newData) {
        return service.update(newData);
    }
}
