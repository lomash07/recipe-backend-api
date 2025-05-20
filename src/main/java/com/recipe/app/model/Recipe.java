package com.recipe.app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"ingredients", "user"})
@ToString(exclude = {"ingredients", "user"})
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "creator_name", nullable = false)
    private String creatorName;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<Ingredient> ingredients = new ArrayList<>();

    // Enum for difficulty levels
    public enum Difficulty {
        Easy, Medium, Hard
    }

    // Helper method to add an ingredient and set the relationship
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        ingredient.setRecipe(this);
    }

    // Helper method to remove an ingredient
    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
        ingredient.setRecipe(null);
    }
}
