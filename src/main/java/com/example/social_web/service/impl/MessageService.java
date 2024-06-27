package com.example.social_web.service.impl;

import com.example.social_web.entity.Message;
import com.example.social_web.entity.User;
import com.example.social_web.mapper.MessageMapper;
import com.example.social_web.payload.request.MessageCreateDTO;
import com.example.social_web.payload.response.MessageResponse;
import com.example.social_web.repo.ChatRepository;
import com.example.social_web.repo.MessageRepository;
import com.example.social_web.repo.UserRepository;
import com.example.social_web.service.IMessageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageService implements IMessageService {
    ChatRepository chatRepository;
    MessageRepository messageRepository;
    MessageMapper mapper;
    UserRepository userRepository;


    @Override
    public MessageResponse sendMessage(Authentication authentication, Integer chatId, MessageCreateDTO messageCreateDTO) {
        User user = (User) authentication.getPrincipal();
        var chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("chat not found"));
        user = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("user not found"));
        Message message = mapper.mapDTO(messageCreateDTO);
        message.setChat(chat);
        message.setUser(user);
        messageRepository.save(message);
        return mapper.mapResponse(message);
    }

    @Override
    public List<MessageResponse> getAllMessageByChatId(Integer chatId) {
        chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("chat not found"));
        List<Message> messages = messageRepository.findAllByChatId(chatId);
        return messages.stream().map(mapper::mapResponse).collect(Collectors.toList());
    }


}
