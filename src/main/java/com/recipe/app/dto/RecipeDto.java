package com.recipe.app.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.recipe.app.model.Recipe.Difficulty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDto {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Difficulty is required")
    private Difficulty difficulty;

    @NotBlank(message = "Instructions are required")
    private String instructions;

    private String image_url;

    @NotBlank(message = "Creator name is required")
    private String creator_name;

    private Date created_date;

    private Long user_id;

    @NotEmpty(message = "At least one ingredient is required")
    @Builder.Default
    private List<IngredientDto> ingredients = new ArrayList<>();
}