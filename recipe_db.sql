CREATE DATABASE  IF NOT EXISTS `recipe_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `recipe_db`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: recipe_db
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ingredients`
--
use root

DROP TABLE IF EXISTS `ingredients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredients` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `recipe_id` bigint NOT NULL,
  `ingredient_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `recipe_id` (`recipe_id`),
  CONSTRAINT `ingredients_ibfk_1` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredients`
--

LOCK TABLES `ingredients` WRITE;
/*!40000 ALTER TABLE `ingredients` DISABLE KEYS */;
INSERT INTO `ingredients` VALUES (1,1,'1 1/2 cups all-purpose flour'),(2,1,'3 1/2 teaspoons baking powder'),(3,1,'1 teaspoon salt'),(4,1,'1 tablespoon white sugar'),(5,1,'1 1/4 cups milk'),(6,1,'1 egg'),(7,1,'3 tablespoons butter, melted'),(8,2,'2 tablespoons vegetable oil'),(9,2,'2 cloves garlic, minced'),(10,2,'1 tablespoon fresh ginger, grated'),(11,2,'1 carrot, sliced'),(12,2,'1 cup broccoli florets'),(13,2,'1 bell pepper, sliced'),(14,2,'1 cup bean sprouts'),(15,2,'3 tablespoons soy sauce'),(16,2,'1 tablespoon honey'),(17,3,'1 cup butter, softened'),(18,3,'1 cup white sugar'),(19,3,'1 cup packed brown sugar'),(20,3,'2 eggs'),(21,3,'2 teaspoons vanilla extract'),(22,3,'1 teaspoon baking soda'),(23,3,'2 teaspoons hot water'),(24,3,'1/2 teaspoon salt'),(25,3,'3 cups all-purpose flour'),(26,3,'2 cups chocolate chips'),(27,4,'9 lasagna noodles'),(28,4,'1 pound ground beef'),(29,4,'1 onion, chopped'),(30,4,'2 cloves garlic, minced'),(31,4,'1 can crushed tomatoes'),(32,4,'2 cans tomato paste'),(33,4,'1/2 cup water'),(34,4,'2 tablespoons Italian seasoning'),(35,4,'16 ounces ricotta cheese'),(36,4,'1 egg'),(37,4,'4 cups mozzarella cheese, shredded'),(38,4,'1/2 cup Parmesan cheese, grated'),(39,5,'1 cucumber, chopped'),(40,5,'2 tomatoes, chopped'),(41,5,'1 red onion, thinly sliced'),(42,5,'1 bell pepper, chopped'),(43,5,'1/2 cup Kalamata olives'),(44,5,'1 cup feta cheese, crumbled'),(45,5,'1/4 cup olive oil'),(46,5,'2 tablespoons lemon juice'),(47,5,'1 clove garlic, minced'),(48,5,'1 teaspoon dried oregano'),(49,5,'Salt and pepper to taste'),(50,6,'1 cucumber, chopped'),(51,6,'2 tomatoes, chopped'),(52,6,'1 red onion, thinly sliced'),(53,6,'1 bell pepper, chopped'),(54,6,'1/2 cup Kalamata olives'),(55,6,'1 cup feta cheese, crumbled'),(56,6,'1/4 cup olive oil'),(57,6,'2 tablespoons lemon juice'),(61,7,'gghhghg'),(62,7,'bjhbhbhb'),(63,7,'yguygygyg');
/*!40000 ALTER TABLE `ingredients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipes`
--

DROP TABLE IF EXISTS `recipes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `difficulty` enum('Easy','Medium','Hard') NOT NULL,
  `instructions` text,
  `image_url` varchar(255) DEFAULT NULL,
  `creator_name` varchar(255) NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_recipe_user` (`user_id`),
  CONSTRAINT `fk_recipe_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipes`
--

LOCK TABLES `recipes` WRITE;
/*!40000 ALTER TABLE `recipes` DISABLE KEYS */;
INSERT INTO `recipes` VALUES (1,'Classic Pancakes','Easy','Step 1: Mix flour, sugar, baking powder, and salt in a bowl.\nStep 2: Add milk, eggs, and melted butter. Stir until smooth.\nStep 3: Heat a lightly oiled griddle or frying pan over medium-high heat.\nStep 4: Pour the batter onto the griddle, using approximately 1/4 cup for each pancake.\nStep 5: Cook until bubbles form and the edges are dry, then flip and cook until browned on the other side.','https://images.unsplash.com/photo-1575853121743-60c24f0a7502?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80','John Doe','2023-06-01 02:00:00',1),(2,'Vegetable Stir Fry','Medium','Step 1: Prepare all vegetables by washing and chopping into bite-sized pieces.\nStep 2: Heat oil in a wok or large frying pan over high heat.\nStep 3: Add garlic and ginger, stir for 30 seconds until fragrant.\nStep 4: Add firm vegetables like carrots and broccoli, stir fry for 2 minutes.\nStep 5: Add softer vegetables like bell peppers and bean sprouts, stir fry for 2 more minutes.\nStep 6: Add sauce and toss to coat. Cook for another minute until the sauce thickens.','https://images.unsplash.com/photo-1512621776951-a57141f2eefd?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80','Jane Smith','2023-06-02 07:30:00',2),(3,'Chocolate Chip Cookies','Easy','Step 1: Preheat oven to 350°F (175°C).\nStep 2: Cream together butter, white sugar, and brown sugar until smooth.\nStep 3: Beat in eggs one at a time, then stir in vanilla.\nStep 4: Dissolve baking soda in hot water and add to batter along with salt.\nStep 5: Stir in flour and chocolate chips.\nStep 6: Drop by large spoonfuls onto ungreased pans.\nStep 7: Bake for about 10 minutes, or until edges are nicely browned.','https://images.unsplash.com/photo-1499636136210-6f4ee915583e?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80','Emily Johnson','2023-06-03 01:15:00',3),(4,'Beef Lasagna','Hard','Step 1: Cook lasagna noodles according to package directions.\nStep 2: In a large skillet, cook ground beef until browned. Add onion and garlic, cook until soft.\nStep 3: Stir in crushed tomatoes, tomato paste, water, and Italian seasoning. Simmer for 30 minutes.\nStep 4: In a bowl, mix ricotta cheese, egg, and half of the mozzarella cheese.\nStep 5: In a baking dish, layer sauce, noodles, and cheese mixture. Repeat layers.\nStep 6: Top with remaining mozzarella and Parmesan cheese.\nStep 7: Bake at 375°F (190°C) for 25 minutes covered, then 25 minutes uncovered.','https://images.unsplash.com/photo-1574894709920-11b28e7367e3?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80','Michael Brown','2023-06-04 10:45:00',4),(5,'Greek Salad','Easy','Step 1: Chop cucumbers, tomatoes, onion, and bell pepper into bite-sized pieces.\nStep 2: Place vegetables in a large bowl.\nStep 3: Add olives and crumbled feta cheese.\nStep 4: In a small bowl, whisk together olive oil, lemon juice, garlic, oregano, salt, and pepper.\nStep 5: Pour dressing over the salad and toss gently to combine.\nStep 6: Refrigerate for at least 30 minutes before serving to allow flavors to blend.','https://images.unsplash.com/photo-1540420773420-3366772f4999?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80','Sarah Wilson','2023-06-05 04:20:00',NULL),(6,'Greek Salad1','Easy','Step 1: Chop cucumbers, tomatoes, onion, and bell pepper into bite-sized pieces.\nStep 2: Place vegetables in a large bowl.\nStep 3: Add olives and crumbled feta cheese.\nStep 4: In a small bowl, whisk together olive oil, lemon juice, garlic, oregano, salt, and pepper.\nStep 5: Pour dressing over the salad and toss gently to combine.\n','https://images.unsplash.com/photo-1540420773420-3366772f4999?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80','k','2023-06-05 04:20:00',6),(7,'jhhghhg','Easy','huhjyhjyjyghygjh','','k','2025-05-20 05:09:44',6);
/*!40000 ALTER TABLE `recipes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'johndoe','John Doe','john@example.com','$2a$10$ixlPY3AAd4ty1l6E2IsXUOXLg2PiGlGK0BIE7/d.RFyUW0AEMZPl.'),(2,'janesmith','Jane Smith','jane@example.com','$2a$10$ixlPY3AAd4ty1l6E2IsXUOXLg2PiGlGK0BIE7/d.RFyUW0AEMZPl.'),(3,'emilyjohnson','Emily Johnson','emily@example.com','$2a$10$ixlPY3AAd4ty1l6E2IsXUOXLg2PiGlGK0BIE7/d.RFyUW0AEMZPl.'),(4,'michaelbrown','Michael Brown','michael@example.com','$2a$10$ixlPY3AAd4ty1l6E2IsXUOXLg2PiGlGK0BIE7/d.RFyUW0AEMZPl.'),(5,'sarahwilson','Sarah Wilson','sarah@example.com','$2a$10$ixlPY3AAd4ty1l6E2IsXUOXLg2PiGlGK0BIE7/d.RFyUW0AEMZPl.'),(6,'Lomash','k','growupkid27@gmail.com','$2a$10$AMs1PxswqyOaLdDCtsNcU.ffA6WqBWl4Hob5NvgafPmsm272uvfZe'),(8,'Divyansh','Divyansh','divyansh@gmail.com','$2a$10$h2L4FYrT1KwdsJ8HFnxzau8VmA0NVtWZ6hEwBsErvP8enS9LYgTlm');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-21  2:27:42
