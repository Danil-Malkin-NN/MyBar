package ru.nino.mybar.service;

import org.springframework.stereotype.Service;
import ru.nino.mybar.dto.show.StepDto;
import ru.nino.mybar.entity.Step;
import ru.nino.mybar.mapper.impl.StepsMapperImpl;
import ru.nino.mybar.repository.impl.StepsRepositoryImpl;

@Service
public class StepsServiceImpl extends CRUDService<StepDto, Step> {

    public StepsServiceImpl(StepsRepositoryImpl repository, StepsMapperImpl mapper) {
        super(repository, mapper);
    }

    @Override
    protected String getEntityName() {
        return "Steps";
    }

}
