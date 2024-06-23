package com.example.social_web.repo;

import com.example.social_web.entity.Token;
import com.example.social_web.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByTokenAndType(String token, TokenType type);
}
