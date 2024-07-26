package com.example.social_web.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    Integer userId;
    Integer postId;
    String content;
    String imageUrl;
    String username;
    String userImage;
    LocalDateTime publishDate;
    Integer totalLike;
    Integer totalComment;

}
