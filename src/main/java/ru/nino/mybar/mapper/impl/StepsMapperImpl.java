package ru.nino.mybar.mapper.impl;

import org.mapstruct.Mapper;
import ru.nino.mybar.dto.show.StepDto;
import ru.nino.mybar.entity.Step;
import ru.nino.mybar.mapper.AllMapper;

@Mapper
public interface StepsMapperImpl extends AllMapper<StepDto, Step> {
}
