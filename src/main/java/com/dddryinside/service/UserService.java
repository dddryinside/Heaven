package com.dddryinside.service;

import com.dddryinside.DTO.RegistrationRequest;
import com.dddryinside.DTO.UpdateProfileRequest;
import com.dddryinside.model.User;
import com.dddryinside.repository.UserRepository;
import com.dddryinside.value.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            throw new RuntimeException("No user is authenticated");
        }
        UserDetails userDetails =  (UserDetails) authentication.getPrincipal();
        Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());
        return userOptional.orElse(null);
    }

    public void registerUser(RegistrationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    public void updateProfile(String name, String about) {
        User user = getCurrentUser();
        user.setName(name);
        user.setAbout(about);
        userRepository.save(user);
    }
}
