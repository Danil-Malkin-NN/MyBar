package ru.nino.mybar.controller;

import java.util.List;

public interface CRUDController<DTO, ENTITY> {

    List<DTO> getAll();

    DTO getById(Integer id);

    DTO create(DTO newObject);

    void delete(Integer id);

    DTO update(DTO newData);

}
