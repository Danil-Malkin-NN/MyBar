package ru.nino.mybar.repository.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.user.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepositoryImpl extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String username);


}
