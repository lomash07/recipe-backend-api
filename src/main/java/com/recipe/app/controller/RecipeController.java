package com.recipe.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.app.dto.RecipeDto;
import com.recipe.app.security.CustomUserDetailsService;
import com.recipe.app.service.RecipeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/recipes")
@CrossOrigin(origins= "*")
@RequiredArgsConstructor
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;
    private final CustomUserDetailsService userDetailsService;

    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        log.info("Request to get all recipes");
        List<RecipeDto> recipes = recipeService.getAllRecipes();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }
    @GetMapping("/my-recipes")
    public ResponseEntity<List<RecipeDto>> getMyRecipes(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Request to get recipes for user: {}", userDetails.getUsername());
        Long userId = userDetailsService.getUserIdByUsername(userDetails.getUsername());
        List<RecipeDto> recipes = recipeService.getRecipesByUserId(userId);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long id) {
        log.info("Request to get recipe with id: {}", id);
        RecipeDto recipe = recipeService.getRecipeById(id);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(
            @Valid @RequestBody RecipeDto recipeDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        log.info("Request to create recipe by user: {}", userDetails.getUsername());
        Long userId = userDetailsService.getUserIdByUsername(userDetails.getUsername());
        RecipeDto createdRecipe = recipeService.createRecipe(recipeDto, userId);
        return new ResponseEntity<>(createdRecipe, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(
            @PathVariable Long id,
            @Valid @RequestBody RecipeDto recipeDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        log.info("Request to update recipe with id: {} by user: {}", id, userDetails.getUsername());
        Long userId = userDetailsService.getUserIdByUsername(userDetails.getUsername());
        RecipeDto updatedRecipe = recipeService.updateRecipe(id, recipeDto, userId);
        return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        log.info("Request to delete recipe with id: {} by user: {}", id, userDetails.getUsername());
        Long userId = userDetailsService.getUserIdByUsername(userDetails.getUsername());
        boolean deleted = recipeService.deleteRecipe(id, userId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}