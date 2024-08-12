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
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            throw new RuntimeException("No user is authenticated");
        }
        UserDetails userDetails =  (UserDetails) authentication.getPrincipal();
        Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());
        return userOptional.orElse(null);
    }

    public void registerUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    public void updateProfile(String name, String about, MultipartFile avatar) throws Exception {
        User user = getCurrentUser();

        if (!avatar.isEmpty()) {
            String avatarUrl = ImageUploader.uploadImageFile(avatar);
            System.out.println(avatarUrl);
            user.setAvatarPhotoUrl(avatarUrl);
        }

        user.setName(name);
        user.setAbout(about);
        userRepository.save(user);
    }
}
