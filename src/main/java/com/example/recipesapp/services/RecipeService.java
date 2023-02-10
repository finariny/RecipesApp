package com.example.recipesapp.services;

import com.example.recipesapp.model.Recipe;

public interface RecipeService {
    long addRecipe(Recipe recipe);

    Recipe getRecipe(long id);
}
