package com.dddryinside.controller.html;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImageController {
    @GetMapping("/upload")
    public String upload() {
        return "upload-image";
    }
}
