package com.dddryinside.service;

import com.dddryinside.DTO.PostCommentRequest;
import com.dddryinside.model.Comment;
import com.dddryinside.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ImageService imageService;

    public CommentService(CommentRepository commentRepository, UserService userService, ImageService imageService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.imageService = imageService;
    }

    public void save(PostCommentRequest postCommentRequest) {
        Comment comment = new Comment();
        comment.setText(postCommentRequest.getText());
        comment.setImage(imageService.getImageById(postCommentRequest.getImageId()));
        comment.setUser(userService.getCurrentUser());
        commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
