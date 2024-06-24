package com.example.social_web.service.impl;

import com.example.social_web.entity.User;
import com.example.social_web.exception.UserMistake;
import com.example.social_web.mapper.UserMapper;
import com.example.social_web.payload.request.PasswordCreateDTO;
import com.example.social_web.payload.response.UserInfoResponse;
import com.example.social_web.repo.UserRepository;
import com.example.social_web.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;



    @Override
    public UserInfoResponse getUserInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        user = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("user not found"));
        return userMapper.mapToResponse(user);
    }

    @Override
    public void createPassword(Authentication authentication, PasswordCreateDTO dto) throws UserMistake {
        User user = (User) authentication.getPrincipal();
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new UserMistake("mis match password");
        }
        user = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("user not found"));
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }

}
