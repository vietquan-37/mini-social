package com.example.social_web.repo;

import com.example.social_web.entity.Oauth2;
import com.example.social_web.enums.OAuth2Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Oauth2Repository extends JpaRepository<Oauth2, Integer> {

    Optional<Oauth2> findByUser_IdAndProvider(Integer userId, OAuth2Provider provider);
}
