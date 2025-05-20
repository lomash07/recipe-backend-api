package com.recipe.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientDto {

    private Long id;

    @NotBlank(message = "Ingredient name is required")
    private String ingredient_name;
}