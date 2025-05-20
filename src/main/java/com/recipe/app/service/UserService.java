package com.recipe.app.service;

import com.recipe.app.dto.UserDto;

public interface UserService {

    /**
     * Register a new user
     *
     * @param userDto the user data
     * @return the registered user
     */
    UserDto registerUser(UserDto userDto);

    /**
     * Get a user by username
     *
     * @param username the username
     * @return the user
     */
    UserDto getUserByUsername(String username);

    /**
     * Get a user by ID
     *
     * @param id the user ID
     * @return the user
     */
    UserDto getUserById(Long id);

    /**
     * Check if a username exists
     *
     * @param username the username to check
     * @return true if exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if an email exists
     *
     * @param email the email to check
     * @return true if exists, false otherwise
     */
    boolean existsByEmail(String email);
}

