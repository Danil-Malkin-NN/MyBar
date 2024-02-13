package ru.nino.mybar.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nino.mybar.dto.user.UserRegistryDTO;
import ru.nino.mybar.entity.user.UserInfo;
import ru.nino.mybar.mapper.AllMapper;

@Mapper
public interface UserRegistryMapper extends AllMapper<UserRegistryDTO, UserInfo> {

    @Override
    @Mapping(target = "user", source = "userCredentialsDto")
    UserInfo toEntity(UserRegistryDTO userRegistryDTO);

    @Override
    @Mapping(target = "userCredentialsDto", ignore = true)
    UserRegistryDTO toDto(UserInfo userInfo);
}
