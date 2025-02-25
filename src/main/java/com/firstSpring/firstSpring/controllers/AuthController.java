package com.firstSpring.firstSpring.controllers;

import com.firstSpring.firstSpring.dto.TokenResponse;
import com.firstSpring.firstSpring.dto.UserLogin;
import com.firstSpring.firstSpring.dto.UserRegister;
import com.firstSpring.firstSpring.service.AuthService;

import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Artist-Code
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/test")
    public String test() {
        return "Hello world";
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody final UserLogin userDTO) {
        final TokenResponse token = authService.login(userDTO);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody final UserRegister userRegister) {
        final TokenResponse token = authService.register(userRegister);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public TokenResponse refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
        return authService.refreshToken(authHeader);
    }
}
