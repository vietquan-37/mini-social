package com.example.social_web.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String content;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    Chat chat;
    LocalDateTime timestamp;
    boolean isDeleted;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private User user;


}
