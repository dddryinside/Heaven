package com.dddryinside.controller.html;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginRegController {
/*    @GetMapping(value = "/login")
    public String login(Model model) {
        return "login-page";
    }

    @GetMapping(value = "/registration")
    public String registration(Model model) {
        return "reg-page";
    }*/

    @GetMapping(value = "/security")
    public String registration(Model model) {
        return "security";
    }
}