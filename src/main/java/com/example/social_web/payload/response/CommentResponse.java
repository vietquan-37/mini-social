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
public class CommentResponse {
    Integer id;
    Integer userId;
    String username;
    String userImage;
    String content;
    LocalDateTime createTime;
}
