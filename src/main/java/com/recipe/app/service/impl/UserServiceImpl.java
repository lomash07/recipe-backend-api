package com.recipe.app.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recipe.app.dto.UserDto;
import com.recipe.app.exception.ResourceNotFoundException;
import com.recipe.app.exception.UserAlreadyExistsException;
import com.recipe.app.model.User;
import com.recipe.app.repository.UserRepository;
import com.recipe.app.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto registerUser(UserDto userDto) {
        log.info("Registering new user with username: {}", userDto.getUsername());

        // Check if username or email already exists
        if (userRepository.existsByUsername(userDto.getUsername())) {
            log.warn("Username already taken: {}", userDto.getUsername());
            throw new UserAlreadyExistsException("Username is already taken: " + userDto.getUsername());
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            log.warn("Email already in use: {}", userDto.getEmail());
            throw new UserAlreadyExistsException("Email is already in use: " + userDto.getEmail());
        }

        // Create new user
        User user = User.builder()
                .username(userDto.getUsername())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();

        // Save user
        User savedUser = userRepository.save(user);
        log.info("User registered successfully with id: {}", savedUser.getId());

        // Convert to DTO without password
        return toDto(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByUsername(String username) {
        log.info("Fetching user by username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        return toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        log.info("Fetching user by id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Helper method to convert User entity to UserDto
    private UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}

