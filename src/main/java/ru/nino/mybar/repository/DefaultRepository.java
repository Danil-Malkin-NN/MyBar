package ru.nino.mybar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DefaultRepository<ENTITY> extends JpaRepository<ENTITY, Integer> {

    Optional<ENTITY> deleteByIdAndReturn(Integer id);
}
