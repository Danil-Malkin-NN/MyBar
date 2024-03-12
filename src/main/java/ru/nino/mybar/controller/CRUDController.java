package ru.nino.mybar.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CRUDController<DTO, ENTITY> {

    List<DTO> getAll();

    Page<DTO> getPage(Pageable pageable);

    DTO getById(Integer id);

    DTO create(DTO newObject);

    void delete(Integer id);

    DTO update(DTO newData);

}
