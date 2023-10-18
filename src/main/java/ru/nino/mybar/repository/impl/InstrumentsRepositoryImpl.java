package ru.nino.mybar.repository.impl;

import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.Instrument;
import ru.nino.mybar.repository.DefaultRepository;

@Repository
public interface InstrumentsRepositoryImpl extends DefaultRepository<Instrument> {
}
