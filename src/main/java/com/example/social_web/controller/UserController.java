package com.example.social_web.controller;
import com.example.social_web.exception.UserMistake;
import com.example.social_web.payload.request.PasswordCreateDTO;
import com.example.social_web.payload.response.APIResponse;
import com.example.social_web.service.IUserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {
    IUserService userService;

    @GetMapping("/info")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> getUserInfo(Authentication authentication) {
        var response = userService.getUserInfo(authentication);
        return ResponseEntity.ok(APIResponse.builder().data(response).status(HttpStatus.OK.value()).build());
    }

    @PatchMapping("/create-password")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> createPassword(Authentication authentication, @RequestBody @Valid PasswordCreateDTO dto) throws UserMistake {
        userService.createPassword(authentication, dto);
        return ResponseEntity.ok(APIResponse.builder().data("password create successfully").status(HttpStatus.OK.value()).build());
    }
}
