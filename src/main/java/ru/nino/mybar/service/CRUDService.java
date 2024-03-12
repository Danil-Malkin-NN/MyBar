package ru.nino.mybar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.nino.mybar.controller.CRUDController;
import ru.nino.mybar.entity.IdEntity;
import ru.nino.mybar.mapper.AllMapper;
import ru.nino.mybar.repository.DefaultRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class CRUDService<DTO, ENTITY extends IdEntity> implements CRUDController<DTO, ENTITY> {

    protected final DefaultRepository<ENTITY> repository;

    protected final AllMapper<DTO, ENTITY> mapper;

    @Override
    public Page<DTO> getPage(Pageable pageable) {
        return repository.findAll(pageable)
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
    public DTO getById(Integer id) {
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
    public void delete(Integer id) {
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
