package com.example.social_web.controller;

import com.example.social_web.payload.response.APIResponse;
import com.example.social_web.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(APIResponse.builder().data(response).build());
    }
}
