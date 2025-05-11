package ru.nino.mybar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.nino.mybar.entity.IdEntity;

import java.util.List;

public interface NameFinderRepository<ENTITY extends IdEntity> extends DefaultRepository<ENTITY> {

    default List<ENTITY> findTop5ByNameLikeIgnoreCase(String name) {
        throw new UnsupportedOperationException("Метод findTop5ByNameLikeIgnoreCase не переопределён в этом репозитории.");
    }

    default ENTITY findByName(String s) {
        throw new UnsupportedOperationException("Метод findByName не переопределён в этом репозитории.");
    }

    default Page<ENTITY> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        throw new UnsupportedOperationException("Метод findByNameContainingIgnoreCase не переопределён в этом репозитории.");
    }
}
