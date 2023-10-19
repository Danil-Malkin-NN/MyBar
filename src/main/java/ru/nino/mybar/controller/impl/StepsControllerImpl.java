package ru.nino.mybar.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nino.mybar.controller.CRUDController;
import ru.nino.mybar.dto.show.StepDto;
import ru.nino.mybar.entity.Step;
import ru.nino.mybar.service.StepsServiceImpl;

import java.util.List;

@RequestMapping("steps")
@RestController
@RequiredArgsConstructor
public class StepsControllerImpl implements CRUDController<StepDto, Step> {

    private final StepsServiceImpl service;

    @Override
    @GetMapping("all")
    public List<StepDto> getAll() {
        return service.getAll();
    }

    @Override
    @GetMapping("{id}")
    public StepDto getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @Override
    @PostMapping("create")
    public StepDto create(@RequestBody StepDto newObject) {
        return service.create(newObject);
    }

    @Override
    @DeleteMapping("delete")
    public void delete(@RequestParam Integer id) {
        service.delete(id);
    }

    @Override
    @PutMapping("update")
    public StepDto update(@RequestBody StepDto newData) {
        return service.update(newData);
    }
}
