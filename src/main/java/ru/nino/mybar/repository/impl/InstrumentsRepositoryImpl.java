package ru.nino.mybar.repository.impl;

import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.Instrument;
import ru.nino.mybar.repository.NameFinderRepository;

import java.util.List;

@Repository
public interface InstrumentsRepositoryImpl extends NameFinderRepository<Instrument> {

    List<Instrument> findTop5ByNameLikeIgnoreCase(String name);

    @Override
    Instrument findByName(String s);

}
