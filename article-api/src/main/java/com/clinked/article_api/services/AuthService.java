package com.clinked.article_api.services;

import com.clinked.article_api.dtos.request.CreateUserDto;
import com.clinked.article_api.dtos.request.LoginUserDto;
import com.clinked.article_api.models.User;
import com.clinked.article_api.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(CreateUserDto input) {
        try {
            User user = new User();
            user.setFirstName(input.getFirstName());
            user.setLastName(input.getLastName());
            user.setUsername(input.getUsername());
            user.setPassword(passwordEncoder.encode(input.getPassword()));

            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during user registration: " + e.getMessage(), e);
        }
    }

    public UserDetails authenticate(LoginUserDto input) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getUsername(),
                            input.getPassword()
                    )
            );

            return (UserDetails) Optional.ofNullable(userRepository.findByUsername(input.getUsername()))
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred during authentication: " + e.getMessage(), e);
        }
    }
}