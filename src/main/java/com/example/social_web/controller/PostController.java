package com.example.social_web.controller;

import com.example.social_web.payload.request.PostCreateDTO;
import com.example.social_web.payload.response.APIResponse;
import com.example.social_web.service.IPostService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/post")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    IPostService postService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> getAllPost() {
        var response = postService.getAllPosts();
        return ResponseEntity.ok(APIResponse.builder().data(response).status(HttpStatus.OK.value()).build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> createPost(@RequestBody @Valid PostCreateDTO dto, Authentication authentication) {
        var response = postService.createPost(dto, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.builder().data(response).status(HttpStatus.OK.value()).build());
    }
}
