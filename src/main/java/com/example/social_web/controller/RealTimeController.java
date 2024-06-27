package com.example.social_web.controller;

import com.example.social_web.entity.Message;
import com.example.social_web.payload.response.MessageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RealTimeController {

    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{chatId}")
    public ResponseEntity<MessageResponse> sendToUser(@Payload MessageResponse message,
                                                     @DestinationVariable String chatId) {
        simpMessagingTemplate.convertAndSendToUser(chatId, "/private", message);
        return ResponseEntity.ok(message);
    }
}
