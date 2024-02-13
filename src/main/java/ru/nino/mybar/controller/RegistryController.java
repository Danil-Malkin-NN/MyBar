package ru.nino.mybar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nino.mybar.entity.user.Authorities;
import ru.nino.mybar.entity.user.User;
import ru.nino.mybar.entity.user.UserInfo;
import ru.nino.mybar.repository.impl.UserInfoRepositoryImpl;
import ru.nino.mybar.repository.impl.UserRepositoryImpl;

import java.util.List;

@Tag(name = "Контроллер для регистрации нового пользователя")
@RequestMapping("register")
@RestController()
@RequiredArgsConstructor
public class RegistryController {


    private final UserRepositoryImpl userRepository;
    private final UserInfoRepositoryImpl userInfoRepository;

    private final PasswordEncoder passwordEncoder;


    @Operation(tags = "Метод регистрации пользователя")
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
