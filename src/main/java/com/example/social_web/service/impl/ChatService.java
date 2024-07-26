package com.example.social_web.service.impl;

import com.example.social_web.entity.Chat;
import com.example.social_web.entity.User;
import com.example.social_web.mapper.ChatMapper;
import com.example.social_web.payload.request.ChatCreateDTO;
import com.example.social_web.payload.response.UserChatResponse;
import com.example.social_web.repo.ChatRepository;
import com.example.social_web.repo.UserRepository;
import com.example.social_web.service.IChatService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatService implements IChatService {
    ChatRepository chatRepository;
    UserRepository userRepository;
    ChatMapper chatMapper;

    @Override
    public void createChat(Authentication authentication, ChatCreateDTO dto) {
        User authenticatedUser = (User) authentication.getPrincipal();
        User user = userRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
        User user2 = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        chatRepository.findChatByUsersId(user, user2).orElseGet(() -> {
            Chat chat = new Chat();
            chat.getUsers().add(user);
            chat.getUsers().add(user2);
            chat.setCreatTime(LocalDateTime.now());
            chat.setDeleted(false);
            return chatRepository.save(chat);
        });
    }

    @Override
    public List<UserChatResponse> getUserToChat(Authentication authentication) {
        User authenticatedUser = (User) authentication.getPrincipal();
        List<Chat> chats = chatRepository.findChatsByUsersId(authenticatedUser.getId());

        return chats.stream()
                .flatMap(chat -> chat.getUsers().stream()
                        .filter(user -> !user.getId().equals(authenticatedUser.getId()))
                        .map(user -> chatMapper.toUserChatResponse(chat, user)))
                .collect(Collectors.toList());
    }

}
