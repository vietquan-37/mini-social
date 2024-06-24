package com.example.social_web.service;

import com.example.social_web.exception.EmailExistedException;
import com.example.social_web.exception.UserMistake;
import com.example.social_web.payload.request.*;
import com.example.social_web.payload.response.AuthenticationResponse;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface IAuthService {
     AuthenticationResponse outboundGoogleAuthenticate(String code);
     AuthenticationResponse   outboundGitHubAuthenticate(String code) throws UserMistake;
     AuthenticationResponse authenticate(LoginDTO dto);
     void register(RegisterDTO dto) throws EmailExistedException, MessagingException, UnsupportedEncodingException;
     void verifyEmail(String token) throws MessagingException, UnsupportedEncodingException;
     AuthenticationResponse refreshToken(RefreshTokenDTO dto);

     void ForgotPassword(ForgotPasswordDTO dto) throws MessagingException, UnsupportedEncodingException;
     void resetPassword(String token, PasswordCreateDTO dto) throws MessagingException, UnsupportedEncodingException, UserMistake;
}
