package com.example.social_web.repo;

import com.example.social_web.entity.Chat;
import com.example.social_web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    @Query("SELECT c FROM Chat c WHERE :user MEMBER OF c.users AND :reqUser MEMBER OF c.users")
    Optional<Chat>  findChatByUsersId(@Param("user") User user, @Param("reqUser") User reqUser);
    List<Chat> findChatsByUsersId(Integer userId);
    Optional<Chat> findChatByUsersIdAndId(Integer userId,Integer chatId);
}
