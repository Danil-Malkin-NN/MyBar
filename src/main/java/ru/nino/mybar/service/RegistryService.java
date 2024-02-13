package ru.nino.mybar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nino.mybar.dto.user.UserRegistryDTO;
import ru.nino.mybar.entity.user.Authorities;
import ru.nino.mybar.entity.user.User;
import ru.nino.mybar.entity.user.UserInfo;
import ru.nino.mybar.mapper.impl.UserRegistryMapper;
import ru.nino.mybar.repository.impl.UserInfoRepositoryImpl;
import ru.nino.mybar.repository.impl.UserRepositoryImpl;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistryService {


    private final UserRepositoryImpl userRepository;
    private final UserInfoRepositoryImpl userInfoRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRegistryMapper mapper;

    public UserRegistryDTO createUser(UserRegistryDTO userRegistryDTO){

        final UserInfo user = mapper.toEntity(userRegistryDTO);

        User authorityUser = user.getUser();
        authorityUser.setPassword(passwordEncoder.encode(authorityUser.getPassword()));
        authorityUser.setAuthorities(List.of(new Authorities("USER")));

        User byName = userRepository.findByName(authorityUser.getUsername());
        if (byName == null) {
            UserInfo saved = userInfoRepository.save(user);
            return mapper.toDto(saved);
        }
        return userRegistryDTO;
    }



}
