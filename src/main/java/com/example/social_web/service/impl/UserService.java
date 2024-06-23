package com.example.social_web.service.impl;

import com.example.social_web.entity.User;
import com.example.social_web.mapper.UserMapper;
import com.example.social_web.payload.response.UserInfoResponse;
import com.example.social_web.repo.UserRepository;
import com.example.social_web.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {
    UserRepository userRepository;
    UserMapper userMapper;



    @Override
    public UserInfoResponse getUserInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        user = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("user not found"));
        return userMapper.mapToResponse(user);
    }

}
