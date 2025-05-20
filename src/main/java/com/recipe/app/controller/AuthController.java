package com.recipe.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.app.dto.AuthResponse;
import com.recipe.app.dto.LoginRequest;
import com.recipe.app.dto.UserDto;
import com.recipe.app.security.JwtTokenProvider;
import com.recipe.app.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Processing login request for user: {}", loginRequest.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(userDetails);

        // Get user details
        UserDto userDto = userService.getUserByUsername(loginRequest.getUsername());

        log.info("User logged in successfully: {}", loginRequest.getUsername());
        return ResponseEntity.ok(new AuthResponse(token, userDto));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserDto userDto) {
        log.info("Processing registration request for user: {}", userDto.getUsername());

        // Register the user
        UserDto registeredUser = userService.registerUser(userDto);

        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(),
                        userDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(userDetails);

        log.info("User registered and logged in successfully: {}", userDto.getUsername());
        return ResponseEntity.ok(new AuthResponse(token, registeredUser));
    }
}