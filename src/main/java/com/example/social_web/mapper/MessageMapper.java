package com.example.social_web.mapper;

import com.example.social_web.entity.Message;
import com.example.social_web.payload.request.MessageCreateDTO;
import com.example.social_web.payload.response.MessageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "content",source = "content")
    @Mapping(target = "timestamp", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDeleted" ,constant = "false")
    Message mapDTO(MessageCreateDTO dto);
    @Mapping(target = "senderId",source = "user.id")
    @Mapping(target = "content",source = "content")
    @Mapping(target = "timestamp",source = "timestamp")
    MessageResponse mapResponse(Message message);
}
