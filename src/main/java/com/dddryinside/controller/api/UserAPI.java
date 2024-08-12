package com.dddryinside.controller.api;

import com.dddryinside.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserAPI {
    private final UserService userService;

    public UserAPI(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registrate")
    public ResponseEntity<Void> registrateUser(@RequestParam("username") String username,
                                               @RequestParam("email") String email,
                                               @RequestParam("password") String password) {
        try {
            userService.registerUser(username, email, password);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestParam("name") String name,
                             @RequestParam("description") String description,
                             @RequestParam("avatar") MultipartFile avatar) {

        try {
            userService.updateProfile(name, description, avatar);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
