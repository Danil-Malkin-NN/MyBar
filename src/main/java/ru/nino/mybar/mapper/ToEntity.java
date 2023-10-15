package ru.nino.mybar.mapper;

public interface ToEntity<DTO, ENTITY> {

    ENTITY toEntity(DTO dto);
}
