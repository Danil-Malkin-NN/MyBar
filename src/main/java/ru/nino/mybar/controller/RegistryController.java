package ru.nino.mybar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nino.mybar.entity.user.Authorities;
import ru.nino.mybar.entity.user.User;
import ru.nino.mybar.entity.user.UserInfo;
import ru.nino.mybar.repository.impl.UserInfoRepositoryImpl;
import ru.nino.mybar.repository.impl.UserRepositoryImpl;

import java.util.List;

@RestController("register")
@RequiredArgsConstructor
public class RegistryController {


    private final UserRepositoryImpl userRepository;
    private final UserInfoRepositoryImpl userInfoRepository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public User registry(@RequestBody UserInfo user) {
        User authorityUser = user.getUser();
        authorityUser.setPassword(passwordEncoder.encode(authorityUser.getPassword()));
        authorityUser.setAuthorities(List.of(new Authorities("USER")));

        User byName = userRepository.findByName(authorityUser.getUsername());
        if (byName == null) {
            UserInfo saved = userInfoRepository.save(user);
            return saved.getUser();
        }
        return authorityUser;
    }


}
