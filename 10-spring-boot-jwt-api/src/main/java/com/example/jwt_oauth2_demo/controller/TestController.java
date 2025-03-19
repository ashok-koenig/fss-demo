package com.example.jwt_oauth2_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/admin/hello")
    String helloAdmin() {
        return "Hello Admin";
    }

    @GetMapping("/user/hello")
    String helloUser() {
        return "Hello User";
    }
}
