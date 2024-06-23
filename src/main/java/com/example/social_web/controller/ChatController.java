package com.example.social_web.controller;

import com.example.social_web.payload.request.ChatCreateDTO;
import com.example.social_web.payload.response.APIResponse;
import com.example.social_web.service.IChatService;
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
@RequestMapping("api/chats")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ChatController {
    IChatService chatService;
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> getChat(Authentication auth) {
        var response =chatService.getUserToChat(auth);
        return ResponseEntity.ok(APIResponse.builder().data(response).status(HttpStatus.OK.value()).build());
    }
    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> CreateChat(Authentication auth, @RequestBody @Valid ChatCreateDTO dto) {
        chatService.createChat(auth, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.builder().data("chat created successfully").status(HttpStatus.CREATED.value()).build());
    }

}
