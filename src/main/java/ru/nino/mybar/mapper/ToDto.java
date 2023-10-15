package ru.nino.mybar.mapper;

public interface ToDto<DTO, ENTITY> {

    DTO toDto(ENTITY entity);
}
