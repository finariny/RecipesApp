package com.example.recipesapp.services.impl;

import com.example.recipesapp.model.Ingredient;
import com.example.recipesapp.services.IngredientService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private static final Map<Long, Ingredient> INGREDIENT_MAP = new HashMap<>();
    private static Long ingredientId = 1L;


    @Override
    public long addIngredient(Ingredient ingredient) {
        if (INGREDIENT_MAP.containsValue(ingredient)) {
            throw new IllegalArgumentException("Такой ингредиент уже существует!");
        } else {
            INGREDIENT_MAP.put(ingredientId, ingredient);
            return ingredientId++;
        }
    }

    @Override
    public Ingredient getIngredient(long id) {
        if (INGREDIENT_MAP.containsKey(id)) {
            return INGREDIENT_MAP.get(id);
        } else {
            throw new IllegalArgumentException("Такого ингредиента нет!");
        }
    }
}
