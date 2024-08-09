package com.dddryinside.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dddryinside.model.Image;
import com.dddryinside.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageService {
    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;

        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dxfefh8r4",
                "api_key", "354627892741836",
                "api_secret", "igf0UBWA_MEvM6NdCaMQHc5lWe0"));
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
}
