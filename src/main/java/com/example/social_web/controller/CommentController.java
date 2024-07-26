package com.example.social_web.controller;

import com.example.social_web.payload.request.CreateCommentDTO;
import com.example.social_web.payload.response.APIResponse;
import com.example.social_web.payload.response.CommentResponse;
import com.example.social_web.service.ICommentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommentController {
    ICommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<APIResponse> createComment(@PathVariable Integer postId, @RequestBody @Valid CreateCommentDTO dto, Authentication authentication) {
        var response=commentService.addComment(postId, dto, authentication);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(APIResponse.builder().status(HttpStatus.CREATED.value()).data(response).build());

    }
    @GetMapping("/{postId}")
    public ResponseEntity<APIResponse> getComments(@PathVariable Integer postId) {
        var response=commentService.getAllComments(postId);
        return ResponseEntity.ok(APIResponse.builder().data(HttpStatus.OK.value()).data(response).build());
    }
    
}
