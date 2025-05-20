package com.recipe.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipe.app.model.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    // Custom query methods can be added here if needed
}
