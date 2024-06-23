package com.example.social_web.mapper;

import com.example.social_web.entity.User;

import com.example.social_web.mapper.passMap.EncodedMapping;
import com.example.social_web.mapper.passMap.PasswordEncoderMapper;
import com.example.social_web.payload.request.RegisterDTO;
import com.example.social_web.payload.response.UserInfoResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = PasswordEncoderMapper.class)
public interface UserMapper {
    @Mapping(target = "username", source="username")
    @Mapping(target = "name", source="name")
    @Mapping(target = "accountLocked" ,constant = "true")
    @Mapping(target = "role", constant="USER")
    @Mapping(target = "telephone", source="phone")
    @Mapping(source = "password",target = "password", qualifiedBy = EncodedMapping.class)
    User registerMapper(RegisterDTO dto);
    @Mapping(target = "id", source="id")
    @Mapping(target = "email", source="username")
    @Mapping(target = "phone", source="telephone")
    @Mapping(source = "password",target = "password")
    @Mapping(target = "avatarUrl", source="imageUrl")
    UserInfoResponse mapToResponse(User user);

}
