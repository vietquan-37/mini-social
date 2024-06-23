package com.example.social_web.service;

import com.example.social_web.payload.request.ChatCreateDTO;
import com.example.social_web.payload.response.UserChatResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IChatService {
    void createChat(Authentication authentication, ChatCreateDTO dto);
    List<UserChatResponse> getUserToChat(Authentication authentication);
}
