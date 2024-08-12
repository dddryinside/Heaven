package com.dddryinside.repository;

import com.dddryinside.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByImageId(Long imageId, Pageable pageable);
    Integer countByImageId(Long imageId);
}
