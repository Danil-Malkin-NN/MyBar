package ru.nino.mybar.repository;

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
