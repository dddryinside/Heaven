package com.dddryinside.controller;

import com.dddryinside.DTO.LoginRequest;
import com.dddryinside.DTO.RegistrationRequest;
import com.dddryinside.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginRegRest {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok("Success!");
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("Success!");
    }
}
