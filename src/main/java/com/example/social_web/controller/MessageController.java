package com.example.social_web.controller;

import com.example.social_web.payload.request.MessageCreateDTO;
import com.example.social_web.payload.response.APIResponse;
import com.example.social_web.service.IMessageService;
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
@RequestMapping("api/message")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MessageController {
    IMessageService messageService;

    @GetMapping("/{chatId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> getAllMessageByChatId(@PathVariable Integer chatId) {
        var response = messageService.getAllMessageByChatId(chatId);
        return ResponseEntity.ok(APIResponse.builder().status(HttpStatus.OK.value()).data(response).build());
    }

    @PostMapping("/create/{chatId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> createMessage(Authentication authentication, @PathVariable Integer chatId, @RequestBody @Valid MessageCreateDTO messageCreateDTO) {
       var response= messageService.sendMessage(authentication, chatId, messageCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.builder()
                .status(HttpStatus.OK.value()).data(response).build());
    }

}
