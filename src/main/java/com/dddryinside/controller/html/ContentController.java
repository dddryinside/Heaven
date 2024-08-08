package com.dddryinside.controller.html;

import com.dddryinside.model.User;
import com.dddryinside.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute(user);

        return "user-page";
    }
}