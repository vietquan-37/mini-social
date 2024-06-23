package com.example.social_web.payload.response;

import com.example.social_web.enums.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    Integer userId;
    String accessToken;
    String refreshToken;
    Role role;

}