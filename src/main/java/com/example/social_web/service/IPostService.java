package com.example.social_web.service;

import com.example.social_web.payload.request.PostCreateDTO;
import com.example.social_web.payload.response.PostResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IPostService {
    Integer createPost(PostCreateDTO dto, Authentication auth);
    List<PostResponse> getAllPosts();
}
