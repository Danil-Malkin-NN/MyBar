package ru.nino.mybar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nino.mybar.entity.IdEntity;

public interface DefaultRepository<ENTITY extends IdEntity> extends JpaRepository<ENTITY, Integer> {

}
