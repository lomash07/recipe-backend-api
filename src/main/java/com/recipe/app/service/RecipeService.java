package com.recipe.app.service;

import java.util.List;

import com.recipe.app.dto.RecipeDto;

public interface RecipeService {

    /**
     * Get all recipes
     *
     * @return List of all recipes
     */
    List<RecipeDto> getAllRecipes();

    /**
     * Get a recipe by its ID
     *
     * @param id the recipe ID
     * @return the recipe or null if not found
     */
    RecipeDto getRecipeById(Long id);

    /**
     * Create a new recipe
     *
     * @param recipeDto the recipe data
     * @param userId the ID of the user creating the recipe
     * @return the created recipe
     */
    RecipeDto createRecipe(RecipeDto recipeDto, Long userId);

    /**
     * Update an existing recipe
     *
     * @param id the recipe ID
     * @param recipeDto the updated recipe data
     * @param userId the ID of the user making the request
     * @return the updated recipe
     */
    RecipeDto updateRecipe(Long id, RecipeDto recipeDto, Long userId);

    /**
     * Delete a recipe by its ID
     *
     * @param id the recipe ID
     * @param userId the ID of the user making the request
     * @return true if deleted, false if not found
     */
    boolean deleteRecipe(Long id, Long userId);

    /**
     * Get recipes by user ID
     *
     * @param userId the user ID
     * @return list of recipes created by the user
     */
    List<RecipeDto> getRecipesByUserId(Long userId);
}