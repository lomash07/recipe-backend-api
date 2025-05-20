package com.recipe.app.config;

import java.util.Arrays;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.recipe.app.model.Ingredient;
import com.recipe.app.model.Recipe;
import com.recipe.app.model.Recipe.Difficulty;
import com.recipe.app.repository.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DataInitializer {

    /**
     * Initialize the database with sample data when the 'dev' profile is active
     */
    @Bean
    @Profile("dev")
    CommandLineRunner initDatabase(RecipeRepository recipeRepository) {
        return args -> {
            log.info("Preloading sample recipes");

            if (recipeRepository.count() == 0) {
                // Recipe 1: Pancakes
                Recipe pancakes = Recipe.builder()
                        .title("Classic Pancakes")
                        .difficulty(Difficulty.Easy)
                        .instructions("Step 1: Mix flour, sugar, baking powder, and salt in a bowl.\n" +
                                "Step 2: Add milk, eggs, and melted butter. Stir until smooth.\n" +
                                "Step 3: Heat a lightly oiled griddle or frying pan over medium-high heat.\n" +
                                "Step 4: Pour the batter onto the griddle, using approximately 1/4 cup for each pancake.\n" +
                                "Step 5: Cook until bubbles form and the edges are dry, then flip and cook until browned on the other side.")
                        .imageUrl("https://images.unsplash.com/photo-1575853121743-60c24f0a7502?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80")
                        .creatorName("John Doe")
                        .createdDate(new Date())
                        .build();

                Arrays.asList(
                        "1 1/2 cups all-purpose flour",
                        "3 1/2 teaspoons baking powder",
                        "1 teaspoon salt",
                        "1 tablespoon white sugar",
                        "1 1/4 cups milk",
                        "1 egg",
                        "3 tablespoons butter, melted"
                ).forEach(name -> {
                    Ingredient ingredient = Ingredient.builder()
                            .ingredientName(name)
                            .build();
                    pancakes.addIngredient(ingredient);
                });

                // Recipe 2: Stir Fry
                Recipe stirFry = Recipe.builder()
                        .title("Vegetable Stir Fry")
                        .difficulty(Difficulty.Medium)
                        .instructions("Step 1: Prepare all vegetables by washing and chopping into bite-sized pieces.\n" +
                                "Step 2: Heat oil in a wok or large frying pan over high heat.\n" +
                                "Step 3: Add garlic and ginger, stir for 30 seconds until fragrant.\n" +
                                "Step 4: Add firm vegetables like carrots and broccoli, stir fry for 2 minutes.\n" +
                                "Step 5: Add softer vegetables like bell peppers and bean sprouts, stir fry for 2 more minutes.\n" +
                                "Step 6: Add sauce and toss to coat. Cook for another minute until the sauce thickens.")
                        .imageUrl("https://images.unsplash.com/photo-1512621776951-a57141f2eefd?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80")
                        .creatorName("Jane Smith")
                        .createdDate(new Date())
                        .build();

                Arrays.asList(
                        "2 tablespoons vegetable oil",
                        "2 cloves garlic, minced",
                        "1 tablespoon fresh ginger, grated",
                        "1 carrot, sliced",
                        "1 cup broccoli florets",
                        "1 bell pepper, sliced",
                        "1 cup bean sprouts",
                        "3 tablespoons soy sauce",
                        "1 tablespoon honey"
                ).forEach(name -> {
                    Ingredient ingredient = Ingredient.builder()
                            .ingredientName(name)
                            .build();
                    stirFry.addIngredient(ingredient);
                });

                recipeRepository.saveAll(Arrays.asList(pancakes, stirFry));
                log.info("Sample data initialized with {} recipes", recipeRepository.count());
            } else {
                log.info("Database already contains data. Skipping initialization.");
            }
        };
    }
}