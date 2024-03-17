package ru.nino.mybar.controller.crud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.nino.mybar.controller.CRUDController;
import ru.nino.mybar.dto.show.InstrumentDto;
import ru.nino.mybar.entity.Instrument;
import ru.nino.mybar.service.InstrumentServiceImpl;

import java.util.List;

@Tag(name = """
		Контроллер для барных инструментов
		""")
@RequestMapping("instruments")
@RestController
@RequiredArgsConstructor
public class InstrumentsControllerImpl implements CRUDController<InstrumentDto, Instrument> {

	private final InstrumentServiceImpl service;

	@GetMapping("name")
	public InstrumentDto searchIngredientByName(String name) {
		return service.getByName(name);
	}

	@GetMapping("search")
	public List<InstrumentDto> searchByName(@RequestParam String name) {
		return service.searchByName(name);
	}

	@Override
	@GetMapping("all")
	@Operation(description = "Отдаёт список барных инструментов")
	public List<InstrumentDto> getAll() {
		return service.getAll();
	}

	@Override
	@GetMapping
	public Page<InstrumentDto> getPage(Pageable pageable) {
		return service.getPage(pageable);
	}

	@Override
	@GetMapping("{id}")
	@Operation(description = "Отдаёт барный инструмент по идентификатору")
	public InstrumentDto getById(@PathVariable Integer id) {
		return service.getById(id);
	}

	@Override
	@PostMapping("create")
	@Operation(description = "Создаёт барный инструмент")
	public InstrumentDto create(@RequestBody InstrumentDto newObject) {
		return service.create(newObject);
	}

	@Override
	@DeleteMapping("delete")
	@Operation(description = "Удаляет барный инструмент по идентификатору")
	public void delete(@RequestParam Integer id) {
		service.delete(id);
	}

	@Override
	@PutMapping("update")
	@Operation(description = "Обновляет барный инструмент")
	public InstrumentDto update(@RequestBody InstrumentDto newData) {
		return service.update(newData);
	}
}
