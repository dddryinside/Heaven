package com.dddryinside.repository;

import com.dddryinside.model.Comment;
import com.dddryinside.model.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByImageId(Long imageId, Pageable pageable);
    Optional<Comment> findById(Long commentId);
    List<Comment> findAllByImage(Image image, Pageable pageable);
    void deleteCommentById(Long commentId);
}
