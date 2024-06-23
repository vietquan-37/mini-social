package com.example.social_web.service;

import com.example.social_web.exception.EmailExistedException;
import com.example.social_web.exception.UserMistake;
import com.example.social_web.payload.request.LoginDTO;
import com.example.social_web.payload.request.RegisterDTO;
import com.example.social_web.payload.response.AuthenticationResponse;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface IAuthService {
     AuthenticationResponse outboundGoogleAuthenticate(String code);
     AuthenticationResponse   outboundGitHubAuthenticate(String code) throws UserMistake;
     AuthenticationResponse authenticate(LoginDTO dto);
     void register(RegisterDTO dto) throws EmailExistedException, MessagingException, UnsupportedEncodingException;
     void verifyEmail(String token) throws MessagingException, UnsupportedEncodingException;
}
