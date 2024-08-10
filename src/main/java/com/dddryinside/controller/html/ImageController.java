package com.dddryinside.controller.html;

import com.dddryinside.model.Image;
import com.dddryinside.repository.ImageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ImageController {
    private final ImageRepository imageRepository;

    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload-image";
    }

    @GetMapping("/image/{id}")
    public String image(@PathVariable Integer id, Model model) {
        Optional<Image> imageOptional = imageRepository.findById(Long.valueOf(id));
        model.addAttribute("image", imageOptional.orElse(null));
        return "image-page";
    }
}
