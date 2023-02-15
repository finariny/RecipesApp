package com.example.recipesapp.services.impl;

import com.example.recipesapp.model.Ingredient;
import com.example.recipesapp.model.Recipe;
import com.example.recipesapp.services.FilesService;
import com.example.recipesapp.services.IngredientService;
import com.example.recipesapp.services.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Value("${path.to.recipes.file}")
    private String recipesFilePath;

    @Value("${name.of.recipes.file}")
    private String recipesFileName;

    private Map<Long, Recipe> recipeMap = new HashMap<>();
    private static Long recipeId = 1L;

    private final IngredientService ingredientService;

    private final FilesService filesService;

    public RecipeServiceImpl(IngredientService ingredientService, FilesService filesService) {
        this.ingredientService = ingredientService;
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        if (recipeMap.containsValue(recipe)) {
            throw new IllegalArgumentException("Такой рецепт уже существует!");
        } else {
            recipeMap.put(recipeId++, recipe);
            for (Ingredient ingredient : recipe.getIngredientList()) {
                ingredientService.addIngredient(ingredient);
            }
            saveToFile();
            return recipe;
        }
    }

    @Override
    public Recipe getRecipe(long id) {
        return recipeMap.getOrDefault(id, null);
    }

    @Override
    public Recipe editRecipe(long id, Recipe recipe) {
        if (recipeMap.containsKey(id)) {
            recipeMap.replace(id, recipe);
            saveToFile();
            return recipe;
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteRecipe(long id) {
        if (recipeMap.containsKey(id)) {
            recipeMap.remove(id);
            saveToFile();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Map<Long, Recipe> getListOfAllRecipes() {
        return recipeMap;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            filesService.saveToFile(json, Path.of(recipesFilePath, recipesFileName));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        try {
            String json = filesService.readFromFile(Path.of(recipesFilePath, recipesFileName));
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
