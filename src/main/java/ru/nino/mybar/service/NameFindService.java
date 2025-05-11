package ru.nino.mybar.service;

import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.nino.mybar.entity.IdEntity;
import ru.nino.mybar.mapper.AllMapper;
import ru.nino.mybar.repository.NameFinderRepository;

import java.util.List;
import java.util.Optional;


public abstract class NameFindService<DTO, ENTITY extends IdEntity> extends CRUDService<DTO, ENTITY> {

	protected final NameFinderRepository<ENTITY> repository;

	public NameFindService(NameFinderRepository<ENTITY> r, AllMapper<DTO, ENTITY> m) {
		super(r, m);
		repository = r;
	}

	public DTO getByName(String name) {
		return mapper.toDto(repository.findByName(name));
	}

	public List<DTO> searchByName(String name) {
		return repository.findTop5ByNameLikeIgnoreCase(name + "%")
				.stream()
				.map(mapper::toDto)
				.toList();
	}

	public Page<DTO> getPageWithNameFilter(Pageable pageable, String name ) {
		return repository.findByNameContainingIgnoreCase(name, pageable)
				.map(mapper::toDto);
	}

	@Override
	public List<DTO> getAll() {
		return repository.findAll()
				.stream()
				.map(mapper::toDto)
				.toList();
	}

	@Override
	public DTO getById(Long id) {
		return repository.findById(id)
				.map(mapper::toDto)
				.orElseThrow(() -> new ObjectNotFoundException(id, getEntityName()));
	}

	@Override
	@Transactional
	public DTO create(DTO newObject) {
		return Optional.ofNullable(newObject)
				.map(mapper::toEntity)
				.map(repository::save)
				.map(mapper::toDto)
				.orElseThrow(() -> new ObjectNotFoundException(newObject, getEntityName()));
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public DTO update(DTO newData) {
		return Optional.ofNullable(newData)
				.map(mapper::toEntity)
				.map(repository::save)
				.map(mapper::toDto)
				.orElseThrow(() -> new ObjectNotFoundException(newData, getEntityName()));
	}

	protected abstract String getEntityName();
}
