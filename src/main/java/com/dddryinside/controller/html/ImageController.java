package com.dddryinside.controller.html;

import com.dddryinside.model.Image;
import com.dddryinside.service.ImageService;
import com.dddryinside.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ImageController {
    private final ImageService imageService;
    private final UserService userService;

    public ImageController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload-image";
    }

/*    @GetMapping("/image/{id}")
    public String image(@PathVariable Long imageId, Model model) {
        try {
            model.addAttribute("image", imageService.getImageResponse(imageId));
        } catch (Exception e) {

        }
        Image image = imageService.getImageResponse(imageId);
        model.addAttribute("image", image);
        model.addAttribute("isLikedByCurrentUser",
                image.isLikedByCurrentUser(userService.getCurrentUser()));
        return "image-page";
    }*/
}
