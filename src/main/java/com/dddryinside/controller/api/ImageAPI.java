package com.dddryinside.controller.api;

import com.dddryinside.exeptions.APIException;
import com.dddryinside.response.ImageResponse;
import com.dddryinside.service.ImageService;
import com.dddryinside.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageAPI {
    private final ImageService imageService;

    public ImageAPI(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image) {

        try {
            imageService.uploadImage(title, description, image);
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error uploading image: " + e.getMessage());
        }
    }

    @PostMapping("/like/{imageId}")
    public ResponseEntity<Void> like(@PathVariable Long imageId) {
        imageService.like(imageId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("unlike/{imageId}")
    public ResponseEntity<Void> unlikeImage(@PathVariable Long imageId) {
        imageService.unlike(imageId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-image")
    public ResponseEntity<ImageResponse> getImage(@RequestParam Long imageId) {
        try {
            ImageResponse imageResponse = imageService.getImageResponse(imageId);
            return new ResponseEntity<>(imageResponse, HttpStatus.OK);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getHttpStatus());
        }
    }
}

