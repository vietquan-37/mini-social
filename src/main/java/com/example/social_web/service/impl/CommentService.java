package com.example.social_web.service.impl;

import com.example.social_web.entity.Comment;
import com.example.social_web.entity.User;
import com.example.social_web.mapper.CommentMapper;
import com.example.social_web.payload.request.CreateCommentDTO;
import com.example.social_web.payload.response.CommentResponse;
import com.example.social_web.repo.CommentRepository;
import com.example.social_web.repo.PostRepository;
import com.example.social_web.repo.UserRepository;
import com.example.social_web.service.ICommentService;
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
public class CommentService implements ICommentService {
    CommentRepository commentRepository;
    PostRepository postRepository;
    CommentMapper commentMapper;
    UserRepository userRepository;

    @Override
    public CommentResponse addComment(Integer postId, CreateCommentDTO dto, Authentication authentication) {
        var post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post Not Found"));
        User user = (User) authentication.getPrincipal();
        user = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("user not found"));
        Comment comment = commentMapper.mapDTO(dto);
        comment.setPost(post);
        comment.setUser(user);
        commentRepository.save(comment);
        return commentMapper.mapResponse(comment);
    }

    @Override
    public List<CommentResponse> getAllComments(Integer postId) {
        var post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post Not Found"));
        List<Comment> comments = commentRepository.findAllByPostId(post.getId());
        return comments.stream().map(commentMapper::mapResponse).collect(Collectors.toList());
    }


}
