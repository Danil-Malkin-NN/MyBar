package ru.nino.mybar.repository.impl;

import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.Step;
import ru.nino.mybar.repository.DefaultRepository;

@Repository
public interface StepsRepositoryImpl extends DefaultRepository<Step> {
}
