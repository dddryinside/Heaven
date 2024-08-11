package com.dddryinside.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dddryinside.model.Image;
import com.dddryinside.model.User;
import com.dddryinside.repository.ImageRepository;
import com.dddryinside.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class ImageService {
    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public ImageService(ImageRepository imageRepository, UserRepository userRepository,
                        UserService userService) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.userService = userService;

        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dxfefh8r4",
                "api_key", "354627892741836",
                "api_secret", "igf0UBWA_MEvM6NdCaMQHc5lWe0"));
    }

    public Image getImageById(Long id) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        return imageOptional.orElse(null);
    }

    public void uploadImage(String title, String description, MultipartFile file) throws Exception {
        Image image = new Image();
        image.setTitle(title);
        image.setDescription(description);
        image.setUrl(uploadImageFile(file));
        imageRepository.save(image);
    }

    private String uploadImageFile(MultipartFile file) throws IOException {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new IOException("Ошибка при загрузке файла на Cloudinary", e);
        }
    }

    public void like(Long imageId) {
        User user = userService.getCurrentUser();
        Optional<Image> optionalImage = imageRepository.findById(imageId);
        Image image = optionalImage.orElse(null);

        user.getLikedImages().add(image);
        Objects.requireNonNull(image).getLikedByUsers().add(user);

        userRepository.save(user);
        imageRepository.save(image);
    }

    public void unlike(Long imageId) {
        User user = userService.getCurrentUser();
        Optional<Image> optionalImage = imageRepository.findById(imageId);
        Image image = optionalImage.orElse(null);

        user.getLikedImages().remove(image);
        Objects.requireNonNull(image).getLikedByUsers().remove(user);

        userRepository.save(user);
        imageRepository.save(image);
    }
}
