package com.example.social_web.mapper;

import com.example.social_web.entity.Comment;
import com.example.social_web.payload.request.CreateCommentDTO;
import com.example.social_web.payload.response.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "content",source = "content")
    @Mapping(target = "createTime",expression = "java(java.time.LocalDateTime.now())")
    Comment mapDTO(CreateCommentDTO dto);
    @Mapping(target = "id",source = "id")
    @Mapping(target = "userId",source = "user.id")
    @Mapping(target = "createTime",source = "createTime")
    @Mapping(target = "content",source = "content")
    @Mapping(target = "username",source = "user.name")
    @Mapping(target = "userImage",source = "user.imageUrl")
    CommentResponse mapResponse(Comment comment);
}
