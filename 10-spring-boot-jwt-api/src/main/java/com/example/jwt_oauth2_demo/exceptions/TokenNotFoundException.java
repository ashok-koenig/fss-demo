package com.example.jwt_oauth2_demo.exceptions;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String token) {
        super("Token is invalid!: " + token);
    }
}
