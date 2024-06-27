package com.example.social_web.service;

import com.example.social_web.payload.request.MessageCreateDTO;
import com.example.social_web.payload.response.MessageResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IMessageService {
    MessageResponse sendMessage(Authentication authentication,Integer chatId,MessageCreateDTO messageCreateDTO);
    List<MessageResponse> getAllMessageByChatId(Integer chatId);
}
