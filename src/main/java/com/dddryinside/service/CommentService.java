package com.dddryinside.service;

import com.dddryinside.DTO.CommentView;
import com.dddryinside.DTO.PostCommentRequest;
import com.dddryinside.model.Comment;
import com.dddryinside.repository.CommentRepository;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public Integer getCommentsAmount(Long imageId) {
        return commentRepository.countByImageId(imageId);
    }

    public List<CommentView> getComments(Long imageId, int offset, int limit) {
        // Сначала подсчитываем общее количество комментариев для изображения
/*        long totalComments = commentRepository.countByImageId(imageId);

        // Рассчитываем количество страниц (учитываем размер страницы `limit`)
        int totalPages = (int) Math.ceil((double) totalComments / limit);

        // Рассчитываем номер страницы, начиная с конца
        int pageNumber = totalPages - 1 - (offset / limit);
        if (pageNumber < 0) {
            pageNumber = 0;
        }*/

        /*List<Comment> comments = commentRepository.findByImageId(imageId, PageRequest.of(pageNumber, limit)).getContent();*/

        List<Comment> comments = commentRepository.findByImageId(
                imageId,
                PageRequest.of(offset / limit, limit, Sort.by(Sort.Direction.DESC, "id"))
        ).getContent();

        // Преобразуем в CommentView
        List<CommentView> viewComments = new ArrayList<>();
        for (Comment comment : comments) {
            CommentView commentView = new CommentView();
            commentView.setUsername(comment.getUser().getUsername());
            commentView.setText(comment.getText());
            viewComments.add(commentView);
        }

        return viewComments;
    }


    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
