package com.clinked.article_api.controllers;

import com.clinked.article_api.models.User;
import com.clinked.article_api.dtos.request.LoginUserDto;
import com.clinked.article_api.dtos.request.CreateUserDto;
import com.clinked.article_api.dtos.response.LoginResponseDto;
import com.clinked.article_api.services.AuthService;
import com.clinked.article_api.utils.JwtUtil;
import com.clinked.article_api.utils.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    public AuthController(JwtUtil jwtUtil, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> register(@RequestBody CreateUserDto createUserDto) {
        try {
            User registeredUser = authService.signup(createUserDto);
            return ResponseUtil.success("User registered successfully", registeredUser);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to register user", 500, e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            UserDetails authenticatedUser = authService.authenticate(loginUserDto);
            String jwtToken = jwtUtil.generateToken(authenticatedUser);

            LoginResponseDto loginResponse = new LoginResponseDto();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtUtil.getExpirationTime());

            return ResponseUtil.success("User authenticated successfully", loginResponse);
        } catch (Exception e) {
            return ResponseUtil.error("Authentication failed", 401, e.getMessage());
        }
    }
}
