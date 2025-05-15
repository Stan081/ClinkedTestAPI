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

/**
 * Service class for handling authentication-related operations.
 */
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructor for AuthService.
     *
     * @param userRepository        Repository for user data.
     * @param authenticationManager Manager for authentication operations.
     * @param passwordEncoder       Encoder for password encryption.
     */
    public AuthService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user in the system.
     *
     * @param input Data transfer object containing user registration details.
     * @return The created User object.
     * @throws RuntimeException if an error occurs during user registration.
     */
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

    /**
     * Authenticates a user based on their credentials.
     *
     * @param input Data transfer object containing login details.
     * @return UserDetails object of the authenticated user.
     * @throws RuntimeException if authentication fails or an error occurs.
     */
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