package ru.nino.mybar.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nino.mybar.controller.CRUDController;
import ru.nino.mybar.dto.show.InstrumentDto;
import ru.nino.mybar.entity.Instrument;
import ru.nino.mybar.service.InstrumentServiceImpl;

import java.util.List;

@RequestMapping("instruments")
@RestController
@RequiredArgsConstructor
public class InstrumentsControllerImpl implements CRUDController<InstrumentDto, Instrument> {

    private final InstrumentServiceImpl service;

    @Override
    @GetMapping("all")
    public List<InstrumentDto> getAll() {
        return service.getAll();
    }

    @Override
    @GetMapping("{id}")
    public InstrumentDto getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @Override
    @PostMapping("create")
    public InstrumentDto create(@RequestBody InstrumentDto newObject) {
        return service.create(newObject);
    }

    @Override
    @DeleteMapping("delete")
    public void delete(@RequestParam Integer id) {
        service.delete(id);
    }

    @Override
    @PutMapping("update")
    public InstrumentDto update(@RequestBody InstrumentDto newData) {
        return service.update(newData);
    }
}
