package ru.nino.mybar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nino.mybar.dto.user.UserRegistryDTO;
import ru.nino.mybar.entity.user.Authorities;
import ru.nino.mybar.entity.user.User;
import ru.nino.mybar.entity.user.UserInfo;
import ru.nino.mybar.repository.impl.UserInfoRepositoryImpl;
import ru.nino.mybar.repository.impl.UserRepositoryImpl;
import ru.nino.mybar.service.RegistryService;

import java.util.List;

@Tag(name = "Контроллер для регистрации нового пользователя")
@RequestMapping("register")
@RestController()
@RequiredArgsConstructor
public class RegistryController {



    private final RegistryService registryService;


    @Operation(tags = "Метод регистрации пользователя")
    @PostMapping
    public UserRegistryDTO registry(@RequestBody @Valid UserRegistryDTO user) {
        return registryService.createUser(user);
    }


}
