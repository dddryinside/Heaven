package com.dddryinside.controller.html;

import com.dddryinside.model.User;
import com.dddryinside.repository.ImageRepository;
import com.dddryinside.repository.UserRepository;
import com.dddryinside.service.ImageService;
import com.dddryinside.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    private final UserService userService;
    private final ImageService imageService;

    public UserController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute(user);
        model.addAttribute("photoGallery", imageService.getImagesByUser(user));
        return "user-page";
    }

    @GetMapping("/edit/profile")
    public String editProfile(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute(user);
        return "edit-profile";
    }
}