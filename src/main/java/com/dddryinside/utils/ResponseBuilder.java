package com.dddryinside.utils;


import com.dddryinside.model.Image;
import com.dddryinside.repository.ImageRepository;
import com.dddryinside.response.ImageResponse;
import org.springframework.stereotype.Service;

@Service
public class ResponseBuilder {
    private final ImageRepository imageRepository;

    public ResponseBuilder(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ImageResponse buildImageResponse(Image image) {
        return new ImageResponse(image.getId(),
                image.getTitle(),
                image.getDescription(),
                image.getUrl(),
                imageRepository.getImageLikesAmount(image.getId()),
                null, null);
    }
}
