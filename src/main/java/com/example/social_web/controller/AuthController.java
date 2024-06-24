package com.example.social_web.controller;

import com.example.social_web.exception.EmailExistedException;
import com.example.social_web.exception.UserMistake;
import com.example.social_web.payload.request.*;
import com.example.social_web.payload.response.APIResponse;
import com.example.social_web.service.IAuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthController {
    IAuthService authService;

    @PostMapping("/outbound/google/authentication")
    ResponseEntity<APIResponse> outboundGoogleAuthenticate(
            @RequestParam("code") String code
    ) {
        var result = authService.outboundGoogleAuthenticate(code);
        return ResponseEntity.ok(APIResponse.builder().status(HttpStatus.OK.value()).data(result).build());
    }

    @PostMapping("/outbound/github/authentication")
    ResponseEntity<APIResponse> outboundGitHubAuthenticate(
            @RequestParam("code") String code
    ) throws UserMistake {
        var result = authService.outboundGitHubAuthenticate(code);
        return ResponseEntity.ok(APIResponse.builder().status(HttpStatus.OK.value()).data(result).build());
    }

    @PostMapping("/register")
    ResponseEntity<APIResponse> register(@RequestBody @Valid RegisterDTO registerDTO) throws MessagingException, UnsupportedEncodingException, EmailExistedException {
        authService.register(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.builder().status(HttpStatus.CREATED.value()).data("User have been register successfully").build());
    }

    @PostMapping("/authenticate")
    ResponseEntity<APIResponse> register(@RequestBody @Valid LoginDTO dto) {
        var response = authService.authenticate(dto);
        return ResponseEntity.ok(APIResponse.builder().status(HttpStatus.OK.value()).data(response).build());
    }

    @PostMapping("/verify-email")
    ResponseEntity<APIResponse> verify(@RequestParam String token) throws MessagingException, UnsupportedEncodingException {
        authService.verifyEmail(token);
        return ResponseEntity.ok(APIResponse.builder().status(HttpStatus.OK.value()).data("Account has been verified").build());
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<APIResponse> refreshToken(
            @Valid  @RequestBody RefreshTokenDTO request
    ) {
        return ResponseEntity.ok(APIResponse.builder().status(HttpStatus.OK.value())
                .data(authService.refreshToken(request)).build());
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<APIResponse> forgotPassword(
            @Valid  @RequestBody ForgotPasswordDTO request
    ) throws MessagingException, UnsupportedEncodingException {
        authService.ForgotPassword(request);
        return ResponseEntity.ok(APIResponse.builder().status(HttpStatus.OK.value()).data("send forgot password email successfully").build());
    }
    @PostMapping("/reset-password")
    public ResponseEntity<APIResponse> resetPassword(
            @RequestParam String token,     @Valid  @RequestBody PasswordCreateDTO request
    ) throws MessagingException, UnsupportedEncodingException, UserMistake {
        authService.resetPassword(token,request);
        return ResponseEntity.ok(APIResponse.builder().status(HttpStatus.OK.value()).data("reset password successfully").build());
    }
}
