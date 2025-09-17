package com.example.shoeshop.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index"; // Spring Boot sẽ render file src/main/resources/templates/index.html
    }
}
