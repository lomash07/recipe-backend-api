package com.recipe.app.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.recipe.app.dto.IngredientDto;
import com.recipe.app.dto.RecipeDto;
import com.recipe.app.model.Ingredient;
import com.recipe.app.model.Recipe;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecipeMapper {

    public RecipeDto toDto(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        RecipeDto.RecipeDtoBuilder dtoBuilder = RecipeDto.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .difficulty(recipe.getDifficulty())
                .instructions(recipe.getInstructions())
                .image_url(recipe.getImageUrl())
                .creator_name(recipe.getCreatorName())
                .created_date(recipe.getCreatedDate());

        // Set user ID if user exists
        if (recipe.getUser() != null) {
            dtoBuilder.user_id(recipe.getUser().getId());
        }

        // Map ingredients
        List<IngredientDto> ingredientDtos = recipe.getIngredients().stream()
                .map(this::toIngredientDto)
                .collect(Collectors.toList());
        dtoBuilder.ingredients(ingredientDtos);

        return dtoBuilder.build();
    }

    public Recipe toEntity(RecipeDto dto) {
        if (dto == null) {
            return null;
        }

        Recipe.RecipeBuilder recipeBuilder = Recipe.builder()
                .title(dto.getTitle())
                .difficulty(dto.getDifficulty())
                .instructions(dto.getInstructions())
                .imageUrl(dto.getImage_url())
                .creatorName(dto.getCreator_name());

        if (dto.getCreated_date() != null) {
            recipeBuilder.createdDate(dto.getCreated_date());
        }

        Recipe recipe = recipeBuilder.build();

        // Add ingredients
        for (IngredientDto ingredientDto : dto.getIngredients()) {
            Ingredient ingredient = toIngredientEntity(ingredientDto);
            recipe.addIngredient(ingredient);
        }

        return recipe;
    }

    public void updateEntityFromDto(RecipeDto dto, Recipe recipe) {
        recipe.setTitle(dto.getTitle());
        recipe.setDifficulty(dto.getDifficulty());
        recipe.setInstructions(dto.getInstructions());
        recipe.setImageUrl(dto.getImage_url());
        recipe.setCreatorName(dto.getCreator_name());

        // Clear existing ingredients and add new ones
        recipe.getIngredients().clear();
        for (IngredientDto ingredientDto : dto.getIngredients()) {
            Ingredient ingredient = toIngredientEntity(ingredientDto);
            recipe.addIngredient(ingredient);
        }
    }

    private IngredientDto toIngredientDto(Ingredient ingredient) {
        return IngredientDto.builder()
                .id(ingredient.getId())
                .ingredient_name(ingredient.getIngredientName())
                .build();
    }

    private Ingredient toIngredientEntity(IngredientDto dto) {
        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .ingredientName(dto.getIngredient_name());

        if (dto.getId() != null) {
            builder.id(dto.getId());
        }

        return builder.build();
    }

    public List<RecipeDto> toDtoList(List<Recipe> recipes) {
        return recipes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}