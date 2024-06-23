package com.example.social_web.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String content;
    LocalDateTime createTime;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    User user;
    @ManyToOne
    Post post;
}
