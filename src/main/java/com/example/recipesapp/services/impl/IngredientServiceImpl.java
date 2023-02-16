package com.example.recipesapp.services.impl;

import com.example.recipesapp.model.Ingredient;
import com.example.recipesapp.services.FilesService;
import com.example.recipesapp.services.IngredientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Value("${path.to.ingredients.file}")
    private String ingredientsFilePath;

    @Value("${name.of.ingredients.file}")
    private String ingredientsFileName;

    private Map<Long, Ingredient> ingredientMap = new HashMap<>();
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
        ingredientMap.put(ingredientId++, ingredient);
        saveToFile();
        return ingredient;
    }

    @Override
    public Ingredient getIngredient(long id) {
        return ingredientMap.getOrDefault(id, null);
    }

    @Override
    public Ingredient editIngredient(long id, Ingredient ingredient) {
        if (ingredientMap.containsKey(id)) {
            ingredientMap.replace(id, ingredient);
            saveToFile();
            return ingredient;
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteIngredient(long id) {
        if (ingredientMap.containsKey(id)) {
            ingredientMap.remove(id);
            saveToFile();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Map<Long, Ingredient> getListOfAllIngredients() {
        return ingredientMap;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            filesService.saveToFile(json, Path.of(ingredientsFilePath, ingredientsFileName));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        Path path = Path.of(ingredientsFilePath, ingredientsFileName);
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            String json = filesService.readFromFile(path);
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Ingredient>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
