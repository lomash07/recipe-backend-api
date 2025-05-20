package com.recipe.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recipe.app.dto.RecipeDto;
import com.recipe.app.exception.AccessDeniedException;
import com.recipe.app.exception.ResourceNotFoundException;
import com.recipe.app.mapper.RecipeMapper;
import com.recipe.app.model.Recipe;
import com.recipe.app.model.User;
import com.recipe.app.repository.RecipeRepository;
import com.recipe.app.repository.UserRepository;
import com.recipe.app.service.RecipeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDto> getAllRecipes() {
        log.info("Fetching all recipes");
        List<Recipe> recipes = recipeRepository.findAll();
        return recipeMapper.toDtoList(recipes);
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeDto getRecipeById(Long id) {
        log.info("Fetching recipe with id: {}", id);
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + id));
        return recipeMapper.toDto(recipe);
    }

    @Override
    @Transactional
    public RecipeDto createRecipe(RecipeDto recipeDto, Long userId) {
        log.info("Creating new recipe for user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Recipe recipe = recipeMapper.toEntity(recipeDto);

        // Set the user
        recipe.setUser(user);

        // Ensure created date is set if not provided
        if (recipe.getCreatedDate() == null) {
            recipe.setCreatedDate(new java.util.Date());
        }

        Recipe savedRecipe = recipeRepository.save(recipe);
        log.info("Recipe created with id: {}", savedRecipe.getId());
        return recipeMapper.toDto(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeDto updateRecipe(Long id, RecipeDto recipeDto, Long userId) {
        log.info("Updating recipe with id: {} by user with id: {}", id, userId);
        // Check if recipe exists
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + id));

        // Check if user is the owner of the recipe
        if (recipe.getUser() == null || !recipe.getUser().getId().equals(userId)) {
            log.warn("User with id: {} attempted to update recipe with id: {} which they don't own", userId, id);
            throw new AccessDeniedException("You are not authorized to update this recipe");
        }

        // Update recipe from DTO
        recipeMapper.updateEntityFromDto(recipeDto, recipe);
        Recipe updatedRecipe = recipeRepository.save(recipe);
        log.info("Recipe updated successfully");
        return recipeMapper.toDto(updatedRecipe);
    }

    @Override
    @Transactional
    public boolean deleteRecipe(Long id, Long userId) {
        log.info("Deleting recipe with id: {} by user with id: {}", id, userId);
        // Check if recipe exists
        Optional<Recipe> recipeOpt = recipeRepository.findById(id);
        if (!recipeOpt.isPresent()) {
            log.warn("Recipe with id: {} not found for deletion", id);
            return false;
        }

        Recipe recipe = recipeOpt.get();

        // Check if user is the owner of the recipe
        if (recipe.getUser() == null || !recipe.getUser().getId().equals(userId)) {
            log.warn("User with id: {} attempted to delete recipe with id: {} which they don't own", userId, id);
            throw new AccessDeniedException("You are not authorized to delete this recipe");
        }

        recipeRepository.deleteById(id);
        log.info("Recipe deleted successfully");
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDto> getRecipesByUserId(Long userId) {
        log.info("Fetching recipes for user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return recipeMapper.toDtoList(user.getRecipes().stream().toList());
    }
}