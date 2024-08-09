package com.dddryinside.controller.api;

import com.dddryinside.DTO.RegistrationRequest;
import com.dddryinside.DTO.UpdateProfileRequest;
import com.dddryinside.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class UserAPI {
    private final UserService userService;

    public UserAPI(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("Success!");
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam("name") String name,
                             @RequestParam("description") String description,
                             @RequestParam("avatar") MultipartFile avatar) {

        try {
            userService.updateProfile(name, description);
            return "redirect:/home";
        } catch (Exception e) {
            return "redirect:/profile/edit";
        }
    }
}
