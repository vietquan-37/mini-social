package com.example.social_web.entity;

import com.example.social_web.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType type;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}