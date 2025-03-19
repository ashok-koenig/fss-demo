package com.example.jwt_oauth2_demo.controller;

import com.example.jwt_oauth2_demo.entity.Token;
import com.example.jwt_oauth2_demo.entity.User;
import com.example.jwt_oauth2_demo.service.AuthService;
import com.example.jwt_oauth2_demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> signing(@RequestBody User user){
        Token token = authService.signing(user.getUsername(), user.getPassword());
        HashMap<String, String> TokenResponse= new HashMap<String, String>();
        TokenResponse.put("token", token.getToken());
        TokenResponse.put("role", token.getUser().getRole().name());
        return ResponseEntity.ok(TokenResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        return ResponseEntity.ok(userService.register(user));
    }
}
