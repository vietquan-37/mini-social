package com.example.social_web.mapper;

import com.example.social_web.entity.Post;
import com.example.social_web.payload.request.PostCreateDTO;
import com.example.social_web.payload.response.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "content",source = "content")
    @Mapping(target = "publishDate",expression = "java(java.time.LocalDateTime.now())" )
    @Mapping(target = "deleted",constant = "false")
    Post mapToPost(PostCreateDTO dto);
    @Mapping(target = "postId",source = "id")
    @Mapping(target = "content",source = "content")
    @Mapping(target = "publishDate",source = "publishDate")
    @Mapping(target = "userId",source = "user.id")
    @Mapping(target = "username",source = "user.name")
    @Mapping(target = "userImage",source = "user.imageUrl")
    PostResponse mapToPostResponse(Post post);
}
