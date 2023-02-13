package com.example.recipesapp.services.impl;

import com.example.recipesapp.model.Recipe;
import com.example.recipesapp.services.FilesService;
import com.example.recipesapp.services.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static Map<Long, Recipe> RECIPE_MAP = new HashMap<>();
    private static Long recipeId = 1L;

    private final FilesService filesService;

    public RecipeServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        if (RECIPE_MAP.containsValue(recipe)) {
            throw new IllegalArgumentException("Такой рецепт уже существует!");
        } else {
            RECIPE_MAP.put(recipeId++, recipe);
            saveToFile();
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
            saveToFile();
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

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(RECIPE_MAP);
            filesService.saveToRecipesFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        try {
            String json = filesService.readFromRecipesFile();
            RECIPE_MAP = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
