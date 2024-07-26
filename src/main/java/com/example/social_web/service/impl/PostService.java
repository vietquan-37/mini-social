package com.example.social_web.service.impl;

import com.example.social_web.entity.Post;
import com.example.social_web.entity.User;
import com.example.social_web.mapper.PostMapper;
import com.example.social_web.payload.request.PostCreateDTO;
import com.example.social_web.payload.response.PostResponse;
import com.example.social_web.repo.PostRepository;
import com.example.social_web.repo.UserRepository;
import com.example.social_web.service.IPostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService implements IPostService {
    PostRepository postRepository;
    PostMapper postMapper;
    UserRepository userRepository;

    @Override
    public Integer createPost(PostCreateDTO dto, Authentication auth) {
        User user = (User) auth.getPrincipal();
        user = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("user not found"));
        Post post = postMapper.mapToPost(dto);
        post.setUser(user);
        postRepository.save(post);
        return post.getId();
    }

    @Override
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAllByDeletedIsFalseOrderByPublishDateDesc();
        return posts.stream().map(postMapper::mapToPostResponse).collect(Collectors.toList());
    }

}
