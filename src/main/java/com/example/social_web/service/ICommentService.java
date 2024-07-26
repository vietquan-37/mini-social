package com.example.social_web.service;

import com.example.social_web.payload.request.CreateCommentDTO;
import com.example.social_web.payload.response.CommentResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ICommentService {
    CommentResponse addComment(Integer postId,CreateCommentDTO dto, Authentication authentication);
    List<CommentResponse> getAllComments(Integer postId);
}
