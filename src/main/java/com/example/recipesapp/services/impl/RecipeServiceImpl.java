package com.example.recipesapp.services.impl;

import com.example.recipesapp.model.Recipe;
import com.example.recipesapp.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static final Map<Long, Recipe> RECIPE_MAP = new HashMap<>();
    private static Long recipeId = 1L;

    @Override
    public long addRecipe(Recipe recipe) {
        if (RECIPE_MAP.containsValue(recipe)) {
            throw new IllegalArgumentException("Такой рецепт уже существует!");
        } else {
            RECIPE_MAP.put(recipeId, recipe);
            return recipeId++;
        }
    }

    @Override
    public Recipe getRecipe(long id) {
        if (RECIPE_MAP.containsKey(id)) {
            return RECIPE_MAP.get(id);
        } else {
            throw new IllegalArgumentException("Такого рецепта нет!");
        }
    }
}
