package com.example.social_web.service;

import com.example.social_web.exception.UserMistake;
import com.example.social_web.payload.request.PasswordCreateDTO;
import com.example.social_web.payload.response.UserInfoResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
 UserInfoResponse getUserInfo(Authentication authentication);

 void createPassword(Authentication authentication, PasswordCreateDTO dto) throws UserMistake;
}
