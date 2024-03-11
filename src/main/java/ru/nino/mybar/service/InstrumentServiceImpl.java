package ru.nino.mybar.service;

import org.springframework.stereotype.Service;
import ru.nino.mybar.dto.show.InstrumentDto;
import ru.nino.mybar.entity.Instrument;
import ru.nino.mybar.mapper.impl.InstrumentsMapperImpl;
import ru.nino.mybar.repository.impl.InstrumentsRepositoryImpl;

@Service
public class InstrumentServiceImpl extends NameFindService<InstrumentDto, Instrument> {

    public InstrumentServiceImpl(InstrumentsRepositoryImpl repository, InstrumentsMapperImpl mapper) {
        super(repository, mapper);
    }

    @Override
    protected String getEntityName() {
        return "Cocktail";
    }

}
