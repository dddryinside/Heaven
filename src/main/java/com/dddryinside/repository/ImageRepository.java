package com.dddryinside.repository;

import com.dddryinside.model.Image;
import com.dddryinside.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByTitle(String username);
    List<Image> findByUser(User user);
    Optional<Image> findById(Long id);
}
