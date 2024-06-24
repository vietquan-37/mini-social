    package com.example.social_web.payload.response;

    import lombok.*;
    import lombok.experimental.FieldDefaults;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class UserChatResponse {
        Integer chatId;
        Integer userId;
        String userImage;
        String name;

    }
