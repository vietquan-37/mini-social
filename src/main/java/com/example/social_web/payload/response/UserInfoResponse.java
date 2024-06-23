package com.example.social_web.payload.response;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoResponse {
    Integer id;
    String name;
    String avatarUrl;
    String email;
    String password;
    String phone;
}
