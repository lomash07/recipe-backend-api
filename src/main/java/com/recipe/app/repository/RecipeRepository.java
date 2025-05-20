package com.recipe.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipe.app.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // Custom query methods can be added here if needed
}