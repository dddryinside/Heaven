package com.dddryinside.repository;

import com.dddryinside.model.Image;
import com.dddryinside.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByUser(User user);
    Optional<Image> findById(Long id);

    @Query("SELECT COUNT(image_likes) FROM Image image JOIN image.likedByUsers image_likes WHERE image.id = :imageId")
    Long getImageLikesAmount(@Param("imageId") Long imageId);
}
