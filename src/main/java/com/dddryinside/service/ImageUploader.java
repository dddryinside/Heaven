package com.dddryinside.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public class ImageUploader {
    private static final Cloudinary cloudinary =
            new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dxfefh8r4",
                "api_key", "354627892741836",
                "api_secret", "igf0UBWA_MEvM6NdCaMQHc5lWe0"));

    public static String uploadImageFile(MultipartFile file) throws IOException {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new IOException("Ошибка при загрузке файла на Cloudinary", e);
        }
    }
}
