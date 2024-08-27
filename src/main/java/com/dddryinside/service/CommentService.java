package com.dddryinside.service;

import com.dddryinside.exeptions.APIException;
import com.dddryinside.model.Image;
import com.dddryinside.repository.ImageRepository;
import com.dddryinside.requests.EditCommentRequest;
import com.dddryinside.requests.PostCommentRequest;
import com.dddryinside.model.Comment;
import com.dddryinside.repository.CommentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ImageRepository imageRepository;

    public CommentService(CommentRepository commentRepository, UserService userService,
                          ImageRepository imageRepository) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.imageRepository = imageRepository;
    }

    public void save(PostCommentRequest postCommentRequest) throws APIException {
        try {
            Comment comment = new Comment();
            comment.setText(postCommentRequest.getText());

            Optional<Image> imageOptional = imageRepository.findById(postCommentRequest.getImageId());
            if (imageOptional.isPresent()) {
                comment.setImage(imageOptional.get());
            } else {
                throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, "Image not found");
            }

            comment.setUser(userService.getCurrentUser());
            comment.setPostDateTime(LocalDateTime.now());
            comment.setUpdateDateTime(null);
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save comment");
        }
    }

    public void delete(Long commentId) throws APIException {
        try {
            commentRepository.deleteCommentById(commentId);
        } catch (Exception e) {
            throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete comment");
        }
    }

    public void edit(EditCommentRequest editCommentRequest) throws APIException {
        Comment comment = commentRepository.findById(editCommentRequest.getCommentId())
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Comment not found"));

        try {
            comment.setText(editCommentRequest.getNewCommentText());
            comment.setUpdateDateTime(LocalDateTime.now());
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to edit comment");
        }
    }

    public List<Comment> getComments(Long imageId, String pageStr) throws APIException {
        int page;
        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException e) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Invalid page number");
        }

        Pageable pageable = PageRequest.of(page, 5);
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Image not found"));

        try {
            return commentRepository.findAllByImage(image, pageable);
        } catch (Exception e) {
            throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve comments");
        }
    }
}
