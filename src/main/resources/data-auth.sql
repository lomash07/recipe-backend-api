-- src/main/resources/data-auth.sql

-- Create users table if it doesn't exist
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
    );

-- Add foreign key to recipes table if it doesn't exist
ALTER TABLE recipes ADD COLUMN user_id BIGINT;
ALTER TABLE recipes ADD CONSTRAINT fk_recipe_user FOREIGN KEY (user_id) REFERENCES users(id);

-- Insert sample users (password is 'password' encrypted with BCrypt)
INSERT INTO users (username, name, email, password) VALUES
                                                        ('johndoe', 'John Doe', 'john@example.com', '$2a$10$ixlPY3AAd4ty1l6E2IsXUOXLg2PiGlGK0BIE7/d.RFyUW0AEMZPl.'),
                                                        ('janesmith', 'Jane Smith', 'jane@example.com', '$2a$10$ixlPY3AAd4ty1l6E2IsXUOXLg2PiGlGK0BIE7/d.RFyUW0AEMZPl.'),
                                                        ('emilyjohnson', 'Emily Johnson', 'emily@example.com', '$2a$10$ixlPY3AAd4ty1l6E2IsXUOXLg2PiGlGK0BIE7/d.RFyUW0AEMZPl.'),
                                                        ('michaelbrown', 'Michael Brown', 'michael@example.com', '$2a$10$ixlPY3AAd4ty1l6E2IsXUOXLg2PiGlGK0BIE7/d.RFyUW0AEMZPl.'),
                                                        ('sarahwilson', 'Sarah Wilson', 'sarah@example.com', '$2a$10$ixlPY3AAd4ty1l6E2IsXUOXLg2PiGlGK0BIE7/d.RFyUW0AEMZPl.')
    ON DUPLICATE KEY UPDATE
                         name = VALUES(name),
                         email = VALUES(email);

-- Update existing recipes to associate with users
UPDATE recipes SET user_id = 1 WHERE creator_name = 'John Doe' AND user_id IS NULL;
UPDATE recipes SET user_id = 2 WHERE creator_name = 'Jane Smith' AND user_id IS NULL;
UPDATE recipes SET user_id = 3 WHERE creator_name = 'Emily Johnson' AND user_id IS NULL;
UPDATE recipes SET user_id = 4 WHERE creator_name = 'Michael Brown' AND user_id IS NULL;
UPDATE recipes SET user_id = 6 WHERE creator_name = 'k' AND user_id IS NULL;