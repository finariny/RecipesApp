package com.example.recipesapp.services;

import com.example.recipesapp.model.Recipe;

import java.util.Map;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe);

    Recipe getRecipe(long id);

    Recipe editRecipe(long id, Recipe recipe);

    boolean deleteRecipe(long id);

    Map<Long, Recipe> getListOfAllRecipes();
}
