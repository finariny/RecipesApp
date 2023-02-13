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
    public Recipe addRecipe(Recipe recipe) {
        if (RECIPE_MAP.containsValue(recipe)) {
            throw new IllegalArgumentException("Такой рецепт уже существует!");
        } else {
            RECIPE_MAP.put(recipeId++, recipe);
            return recipe;
        }
    }

    @Override
    public Recipe getRecipe(long id) {
        return RECIPE_MAP.getOrDefault(id, null);
    }

    @Override
    public Recipe editRecipe(long id, Recipe recipe) {
        if (RECIPE_MAP.containsKey(id)) {
            RECIPE_MAP.replace(id, recipe);
            return recipe;
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteRecipe(long id) {
        if (RECIPE_MAP.containsKey(id)) {
            RECIPE_MAP.remove(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Map<Long, Recipe> getListOfAllRecipes() {
        return RECIPE_MAP;
    }
}
