package com.example.social_web.service.impl;

import com.example.social_web.config.JwtService;

import com.example.social_web.controller.httpclient.GitHubIdentityClient;
import com.example.social_web.controller.httpclient.GitHubUserClient;
import com.example.social_web.controller.httpclient.OutboundIdentityClient;
import com.example.social_web.controller.httpclient.OutboundUserClient;
import com.example.social_web.email.EmailService;
import com.example.social_web.entity.Oauth2;
import com.example.social_web.entity.Token;
import com.example.social_web.entity.User;
import com.example.social_web.enums.OAuth2Provider;
import com.example.social_web.enums.Role;
import com.example.social_web.enums.TokenType;
import com.example.social_web.exception.EmailExistedException;
import com.example.social_web.exception.UserMistake;
import com.example.social_web.mapper.UserMapper;
import com.example.social_web.payload.request.*;
import com.example.social_web.payload.response.AuthenticationResponse;
import com.example.social_web.repo.Oauth2Repository;
import com.example.social_web.repo.TokenRepository;
import com.example.social_web.repo.UserRepository;
import com.example.social_web.service.IAuthService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService implements IAuthService {
    UserRepository userRepository;
    Oauth2Repository oauth2Repository;
    OutboundIdentityClient identityClient;
    OutboundUserClient userClient;
    JwtService jwtService;
    AuthenticationManager authenticationManager;
    UserMapper userMapper;
    GitHubIdentityClient gitHubIdentityClient;
    GitHubUserClient gitHubUserClient;
    TokenRepository tokenRepository;
    EmailService emailService;
    PasswordEncoder passwordEncoder;
    @NonFinal
    protected static final String GRANT_TYPE = "authorization_code";
    @NonFinal
    @Value("${outbound.identity.client-id}")
    protected String CLIENT_ID;
    static final String VERIFICATION_URL = "http://localhost:8080/api/v1/auth/verify-email?token=";
    static final String RESET_PASSWORD_URL = "http://localhost:4200/api/v1/auth/reset-password?token=";
    @NonFinal
    @Value("${outbound.identity.github.client-id}")
    protected String GITHUB_CLIENT_ID;

    @NonFinal
    @Value("${outbound.identity.github.client-secret}")
    protected String GITHUB_CLIENT_SECRET;
    @NonFinal
    @Value("${outbound.identity.client-secret}")
    protected String CLIENT_SECRET;

    @NonFinal
    @Value("${outbound.identity.redirect-uri}")
    protected String REDIRECT_URI;

    @Override
    public AuthenticationResponse outboundGoogleAuthenticate(String code) {
        var response = identityClient.exchangeToken(ExchangeTokenRequest.builder()
                .code(code)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .redirectUri(REDIRECT_URI)
                .grantType(GRANT_TYPE)
                .build());

        var userInfo = userClient.getUserInfo("json", response.getAccessToken());


        var user = userRepository.findByUsername(userInfo.getEmail()).orElseGet(
                () -> userRepository.save(User.builder()
                        .username(userInfo.getEmail())
                        .name(userInfo.getGivenName() + " " + userInfo.getFamilyName())
                        .imageUrl(userInfo.getPicture())
                        .role(Role.USER)
                        .build()));

        oauth2Repository.findByUser_IdAndProvider(user.getId(), OAuth2Provider.GOOGLE).orElseGet(
                () -> oauth2Repository.save(Oauth2
                        .builder()
                        .provider(OAuth2Provider.GOOGLE)
                        .user(user)
                        .build()
                )
        );


        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken).role(Role.USER).userId(user.getId()).build();
    }

    public AuthenticationResponse outboundGitHubAuthenticate(String code) throws UserMistake {
        Map<String, String> formParams = createFormParams(code);

        var response = gitHubIdentityClient.exchangeToken(formParams);
        var userInfo = gitHubUserClient.getUserInfo("Bearer " + response.getAccessToken());
        String email = userInfo.getEmail();
        if (email == null || email.isBlank()) {
            throw new UserMistake("Please make your email visible");
        }

        var user = userRepository.findByUsername(email).orElseGet(
                () -> userRepository.save(User.builder()
                        .username(userInfo.getEmail())
                        .imageUrl(userInfo.getAvatarUrl())
                        .name(userInfo.getName())
                        .role(Role.USER)
                        .build()));

        oauth2Repository.findByUser_IdAndProvider(user.getId(), OAuth2Provider.GITHUB).orElseGet(
                () -> oauth2Repository.save(Oauth2
                        .builder()
                        .provider(OAuth2Provider.GITHUB)
                        .user(user)
                        .build()
                )
        );

        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(Role.USER)
                .userId(user.getId())
                .build();
    }

    private Map<String, String> createFormParams(String code) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("code", code);
        formParams.put("client_id", GITHUB_CLIENT_ID);
        formParams.put("client_secret", GITHUB_CLIENT_SECRET);
        formParams.put("redirect_uri", REDIRECT_URI);
        formParams.put("grant_type", GRANT_TYPE);
        return formParams;
    }

    @Override
    public AuthenticationResponse authenticate(LoginDTO dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())

        );
        var user = userRepository.findByUsername(dto.getUsername()).orElseThrow();
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder().accessToken(accessToken).role(user.getRole()).refreshToken(refreshToken).userId(user.getId()).build();
    }

    @Override
    public void register(RegisterDTO dto) throws EmailExistedException, MessagingException, UnsupportedEncodingException {
        User user = userMapper.registerMapper(dto);
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new EmailExistedException("Email address already registered");
        }

        userRepository.save(user);
        var token = generateAndSaveToken(user, TokenType.VERIFICATION);
        emailService.sendVerificationEmail(VERIFICATION_URL + token, user.getUsername());
    }

    @Override
    public void verifyEmail(String token) throws MessagingException, UnsupportedEncodingException {
        var verifyToken = tokenRepository.findByTokenAndType(token, TokenType.VERIFICATION).orElseThrow(() -> new EntityNotFoundException("token not found"));
        User user = userRepository.findById(verifyToken.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("user not found"));
        if (verifyToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            var tokenResend = generateAndSaveToken(user, TokenType.VERIFICATION);
            emailService.sendVerificationEmail(VERIFICATION_URL + tokenResend, user.getUsername());
            throw new RuntimeException("Token is expired");
        }
        user.setAccountLocked(false);
        userRepository.save(user);
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenDTO dto) {
        return null;
    }

    @Override
    public void resetPassword(String token, PasswordCreateDTO dto) throws MessagingException, UnsupportedEncodingException, UserMistake {
        Token resetToken = tokenRepository.findByTokenAndType(token, TokenType.RESET).orElseThrow(() -> new EntityNotFoundException("Token not found"));
        if (LocalDateTime.now().isAfter(resetToken.getExpiresAt())) {
            var tokenResend = generateAndSaveToken(resetToken.getUser(), TokenType.RESET);
            emailService.sendResetPasswordEmail(RESET_PASSWORD_URL + tokenResend, resetToken.getUser().getUsername());
            throw new RuntimeException("The verification link has expired. A new link has been sent to your email. Please check your email.");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new UserMistake("The password does not match the confirm password.");
        }
        User user = userRepository.findById(resetToken.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void ForgotPassword(ForgotPasswordDTO dto) throws MessagingException, UnsupportedEncodingException {
        User user = userRepository.findByUsername(dto.getEmail()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        String token = generateAndSaveToken(user, TokenType.RESET);
        emailService.sendResetPasswordEmail(RESET_PASSWORD_URL + token, user.getUsername());
    }

    private String generateAndSaveToken(User user, TokenType type) {
        String generatedToken = UUID.randomUUID().toString();
        Token token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .type(type)
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

}
