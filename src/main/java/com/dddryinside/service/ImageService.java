package com.dddryinside.service;

import com.dddryinside.exeptions.APIException;
import com.dddryinside.model.Image;
import com.dddryinside.model.User;
import com.dddryinside.repository.ImageRepository;
import com.dddryinside.repository.UserRepository;
import com.dddryinside.response.ImageResponse;

import com.dddryinside.utils.ResponseBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ResponseBuilder responseBuilder;
    private final RedisTemplate<String, Object> redisTemplate;

    public ImageService(ImageRepository imageRepository, UserRepository userRepository,
                        UserService userService, ResponseBuilder responseBuilder,
                        RedisTemplate<String, Object> redisTemplate) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.responseBuilder = responseBuilder;
        this.redisTemplate = redisTemplate;
    }



    public ImageResponse getImageResponse(Long imageId) throws APIException {
        String cacheKey = "imageResponse:" + imageId;

        ImageResponse cachedResponse = (ImageResponse) redisTemplate.opsForValue().get(cacheKey);
        if (cachedResponse != null) {
            return cachedResponse;
        } else {
            Optional<Image> imageOptional = imageRepository.findById(imageId);
            if (imageOptional.isPresent()) {
                ImageResponse imageResponse = responseBuilder.buildImageResponse(imageOptional.get());
                redisTemplate.opsForValue().set(cacheKey, imageResponse, 10);
                return imageResponse;
            } else {
                throw new APIException(HttpStatus.NOT_FOUND, "Image not found");
            }
        }
    }


    public void uploadImage(String title, String description, MultipartFile file) throws Exception {
        Image image = new Image();
        image.setTitle(title);
        image.setDescription(description);
        image.setUrl(ImageUploader.uploadImageFile(file));
        image.setUser(userService.getCurrentUser());
        imageRepository.save(image);
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

    public List<Image> getImagesByUser(User user) {
        return imageRepository.findByUser(user);
    }
}
