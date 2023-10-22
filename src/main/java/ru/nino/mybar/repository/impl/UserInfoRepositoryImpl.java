package ru.nino.mybar.repository.impl;

import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.user.UserInfo;
import ru.nino.mybar.repository.DefaultRepository;

@Repository
public interface UserInfoRepositoryImpl extends DefaultRepository<UserInfo> {
}
