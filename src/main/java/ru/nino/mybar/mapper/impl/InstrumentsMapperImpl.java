package ru.nino.mybar.mapper.impl;

import org.mapstruct.Mapper;
import ru.nino.mybar.dto.show.InstrumentDto;
import ru.nino.mybar.entity.Instrument;
import ru.nino.mybar.mapper.AllMapper;

@Mapper
public interface InstrumentsMapperImpl extends AllMapper<InstrumentDto, Instrument> {
}
