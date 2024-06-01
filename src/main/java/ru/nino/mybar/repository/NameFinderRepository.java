package ru.nino.mybar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import ru.nino.mybar.entity.IdEntity;

import java.util.List;

public interface NameFinderRepository<ENTITY extends IdEntity> extends DefaultRepository<ENTITY> {

    default List<ENTITY> findTop5ByNameLikeIgnoreCase(String name) {
        return null;
    }

    default ENTITY findByName(String s) {
        return null;
    }



    ;
}
