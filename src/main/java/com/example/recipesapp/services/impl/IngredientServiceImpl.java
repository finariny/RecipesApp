package com.example.recipesapp.services.impl;

import com.example.recipesapp.model.Ingredient;
import com.example.recipesapp.services.FilesService;
import com.example.recipesapp.services.IngredientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private static Map<Long, Ingredient> INGREDIENT_MAP = new HashMap<>();
    private static Long ingredientId = 1L;

    private final FilesService filesService;

    public IngredientServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (INGREDIENT_MAP.containsValue(ingredient)) {
            throw new IllegalArgumentException("Такой ингредиент уже существует!");
        } else {
            INGREDIENT_MAP.put(ingredientId++, ingredient);
            saveToFile();
            return ingredient;
        }
    }

    @Override
    public Ingredient getIngredient(long id) {
        return INGREDIENT_MAP.getOrDefault(id, null);
    }

    @Override
    public Ingredient editIngredient(long id, Ingredient ingredient) {
        if (INGREDIENT_MAP.containsKey(id)) {
            INGREDIENT_MAP.replace(id, ingredient);
            saveToFile();
            return ingredient;
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteIngredient(long id) {
        if (INGREDIENT_MAP.containsKey(id)) {
            INGREDIENT_MAP.remove(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Map<Long, Ingredient> getListOfAllIngredients() {
        return INGREDIENT_MAP;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(INGREDIENT_MAP);
            filesService.saveToIngredientsFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        try {
            String json = filesService.readFromIngredientsFile();
            INGREDIENT_MAP = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
