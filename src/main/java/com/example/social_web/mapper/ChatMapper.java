package com.example.social_web.mapper;
import com.example.social_web.entity.Chat;
import com.example.social_web.entity.User;
import com.example.social_web.payload.response.UserChatResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface ChatMapper {
    @Mappings({
            @Mapping(source = "chat.id", target = "chatId"),
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "user.imageUrl", target = "userImage"),
            @Mapping(source = "user.name", target = "name"),
    })
    UserChatResponse toUserChatResponse(Chat chat, User user);
}
