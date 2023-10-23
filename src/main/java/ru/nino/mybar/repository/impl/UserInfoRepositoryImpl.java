package ru.nino.mybar.repository.impl;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.user.UserInfo;
import ru.nino.mybar.repository.DefaultRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepositoryImpl extends DefaultRepository<UserInfo> {


    Optional<UserInfo> findByUser_NameIgnoreCase(String name);



}
