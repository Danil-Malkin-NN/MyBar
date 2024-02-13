package ru.nino.mybar.controller.crud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nino.mybar.controller.CRUDController;
import ru.nino.mybar.dto.show.StepDto;
import ru.nino.mybar.entity.Step;
import ru.nino.mybar.service.StepsServiceImpl;

import java.util.List;

@Tag(name = """
        Контроллер для управления шагами приготовления
        """)
@RequestMapping("steps")
@RestController
@RequiredArgsConstructor
public class StepsControllerImpl implements CRUDController<StepDto, Step> {

    private final StepsServiceImpl service;

    @Override
    @GetMapping("all")
    @Operation(description = "Возвращает список всех шагов")
    public List<StepDto> getAll() {
        return service.getAll();
    }

    @Override
    @GetMapping("{id}")
    @Operation(description = "Возвращать шаг по идентификатору")
    public StepDto getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @Override
    @PostMapping("create")
    @Operation(description = "Создаёт шаг")
    public StepDto create(@RequestBody StepDto newObject) {
        return service.create(newObject);
    }

    @Override
    @DeleteMapping("delete")
    @Operation(description = "Удаляет шаг по идентификатору")
    public void delete(@RequestParam Integer id) {
        service.delete(id);
    }

    @Override
    @PutMapping("update")
    @Operation(description = "Обновляет шаг")
    public StepDto update(@RequestBody StepDto newData) {
        return service.update(newData);
    }
}
