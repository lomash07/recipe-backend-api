CREATE DATABASE IF NOT EXISTS recipe_db;
USE recipe_db;

-- Drop tables if they exist to ensure a clean start
DROP TABLE IF EXISTS ingredients;
DROP TABLE IF EXISTS recipes;

-- Create recipes table
CREATE TABLE recipes (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                         difficulty ENUM('Easy', 'Medium', 'Hard') NOT NULL,
                         instructions TEXT,
                         image_url VARCHAR(255),
                         creator_name VARCHAR(255) NOT NULL,
                         created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create ingredients table
CREATE TABLE ingredients (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             recipe_id BIGINT NOT NULL,
                             ingredient_name VARCHAR(255) NOT NULL,
                             FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE
);

-- Insert sample recipes
INSERT INTO recipes (id, title, difficulty, instructions, image_url, creator_name, created_date) VALUES
                                                                                                     (1, 'Classic Pancakes', 'Easy', 'Step 1: Mix flour, sugar, baking powder, and salt in a bowl.\nStep 2: Add milk, eggs, and melted butter. Stir until smooth.\nStep 3: Heat a lightly oiled griddle or frying pan over medium-high heat.\nStep 4: Pour the batter onto the griddle, using approximately 1/4 cup for each pancake.\nStep 5: Cook until bubbles form and the edges are dry, then flip and cook until browned on the other side.', 'https://images.unsplash.com/photo-1575853121743-60c24f0a7502?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80', 'John Doe', '2023-06-01 10:00:00'),

                                                                                                     (2, 'Vegetable Stir Fry', 'Medium', 'Step 1: Prepare all vegetables by washing and chopping into bite-sized pieces.\nStep 2: Heat oil in a wok or large frying pan over high heat.\nStep 3: Add garlic and ginger, stir for 30 seconds until fragrant.\nStep 4: Add firm vegetables like carrots and broccoli, stir fry for 2 minutes.\nStep 5: Add softer vegetables like bell peppers and bean sprouts, stir fry for 2 more minutes.\nStep 6: Add sauce and toss to coat. Cook for another minute until the sauce thickens.', 'https://images.unsplash.com/photo-1512621776951-a57141f2eefd?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80', 'Jane Smith', '2023-06-02 15:30:00'),

                                                                                                     (3, 'Chocolate Chip Cookies', 'Easy', 'Step 1: Preheat oven to 350°F (175°C).\nStep 2: Cream together butter, white sugar, and brown sugar until smooth.\nStep 3: Beat in eggs one at a time, then stir in vanilla.\nStep 4: Dissolve baking soda in hot water and add to batter along with salt.\nStep 5: Stir in flour and chocolate chips.\nStep 6: Drop by large spoonfuls onto ungreased pans.\nStep 7: Bake for about 10 minutes, or until edges are nicely browned.', 'https://images.unsplash.com/photo-1499636136210-6f4ee915583e?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80', 'Emily Johnson', '2023-06-03 09:15:00'),

                                                                                                     (4, 'Beef Lasagna', 'Hard', 'Step 1: Cook lasagna noodles according to package directions.\nStep 2: In a large skillet, cook ground beef until browned. Add onion and garlic, cook until soft.\nStep 3: Stir in crushed tomatoes, tomato paste, water, and Italian seasoning. Simmer for 30 minutes.\nStep 4: In a bowl, mix ricotta cheese, egg, and half of the mozzarella cheese.\nStep 5: In a baking dish, layer sauce, noodles, and cheese mixture. Repeat layers.\nStep 6: Top with remaining mozzarella and Parmesan cheese.\nStep 7: Bake at 375°F (190°C) for 25 minutes covered, then 25 minutes uncovered.', 'https://images.unsplash.com/photo-1574894709920-11b28e7367e3?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80', 'Michael Brown', '2023-06-04 18:45:00'),

                                                                                                     (5, 'Greek Salad', 'Easy', 'Step 1: Chop cucumbers, tomatoes, onion, and bell pepper into bite-sized pieces.\nStep 2: Place vegetables in a large bowl.\nStep 3: Add olives and crumbled feta cheese.\nStep 4: In a small bowl, whisk together olive oil, lemon juice, garlic, oregano, salt, and pepper.\nStep 5: Pour dressing over the salad and toss gently to combine.\nStep 6: Refrigerate for at least 30 minutes before serving to allow flavors to blend.', 'https://images.unsplash.com/photo-1540420773420-3366772f4999?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80', 'Sarah Wilson', '2023-06-05 12:20:00'),
                                                                                                     (6, 'Greek Salad1', 'Easy', 'Step 1: Chop cucumbers, tomatoes, onion, and bell pepper into bite-sized pieces.\nStep 2: Place vegetables in a large bowl.\nStep 3: Add olives and crumbled feta cheese.\nStep 4: In a small bowl, whisk together olive oil, lemon juice, garlic, oregano, salt, and pepper.\nStep 5: Pour dressing over the salad and toss gently to combine.\nStep 6: Refrigerate for at least 30 minutes before serving to allow flavors to blend.', 'https://images.unsplash.com/photo-1540420773420-3366772f4999?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80', 'k', '2023-06-05 12:20:00');

-- Insert sample ingredients
INSERT INTO ingredients (recipe_id, ingredient_name) VALUES
-- Pancakes ingredients
(1, '1 1/2 cups all-purpose flour'),
(1, '3 1/2 teaspoons baking powder'),
(1, '1 teaspoon salt'),
(1, '1 tablespoon white sugar'),
(1, '1 1/4 cups milk'),
(1, '1 egg'),
(1, '3 tablespoons butter, melted'),

-- Vegetable Stir Fry ingredients
(2, '2 tablespoons vegetable oil'),
(2, '2 cloves garlic, minced'),
(2, '1 tablespoon fresh ginger, grated'),
(2, '1 carrot, sliced'),
(2, '1 cup broccoli florets'),
(2, '1 bell pepper, sliced'),
(2, '1 cup bean sprouts'),
(2, '3 tablespoons soy sauce'),
(2, '1 tablespoon honey'),

-- Chocolate Chip Cookies ingredients
(3, '1 cup butter, softened'),
(3, '1 cup white sugar'),
(3, '1 cup packed brown sugar'),
(3, '2 eggs'),
(3, '2 teaspoons vanilla extract'),
(3, '1 teaspoon baking soda'),
(3, '2 teaspoons hot water'),
(3, '1/2 teaspoon salt'),
(3, '3 cups all-purpose flour'),
(3, '2 cups chocolate chips'),

-- Beef Lasagna ingredients
(4, '9 lasagna noodles'),
(4, '1 pound ground beef'),
(4, '1 onion, chopped'),
(4, '2 cloves garlic, minced'),
(4, '1 can crushed tomatoes'),
(4, '2 cans tomato paste'),
(4, '1/2 cup water'),
(4, '2 tablespoons Italian seasoning'),
(4, '16 ounces ricotta cheese'),
(4, '1 egg'),
(4, '4 cups mozzarella cheese, shredded'),
(4, '1/2 cup Parmesan cheese, grated'),

-- Greek Salad ingredients
(5, '1 cucumber, chopped'),
(5, '2 tomatoes, chopped'),
(5, '1 red onion, thinly sliced'),
(5, '1 bell pepper, chopped'),
(5, '1/2 cup Kalamata olives'),
(5, '1 cup feta cheese, crumbled'),
(5, '1/4 cup olive oil'),
(5, '2 tablespoons lemon juice'),
(5, '1 clove garlic, minced'),
(5, '1 teaspoon dried oregano'),
(5, 'Salt and pepper to taste'),

-- Greek Salad ingredients
(6, '1 cucumber, chopped'),
(6, '2 tomatoes, chopped'),
(6, '1 red onion, thinly sliced'),
(6, '1 bell pepper, chopped'),
(6, '1/2 cup Kalamata olives'),
(6, '1 cup feta cheese, crumbled'),
(6, '1/4 cup olive oil'),
(6, '2 tablespoons lemon juice'),
(6, '1 clove garlic, minced'),
(6, '1 teaspoon dried oregano'),
(6, 'Salt and pepper to taste');