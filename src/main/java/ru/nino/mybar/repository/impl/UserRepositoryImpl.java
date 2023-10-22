package ru.nino.mybar.repository.impl;

import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.user.User;
import ru.nino.mybar.repository.DefaultRepository;

@Repository
public interface UserRepositoryImpl extends DefaultRepository<User> {

    User findByName(String username);

}
