package com.example.recipesapp.services;

import com.example.recipesapp.model.Ingredient;

import java.util.Map;

public interface IngredientService {
    long addIngredient(Ingredient ingredient);

    Ingredient getIngredient(long id);

    Ingredient editIngredient(long id, Ingredient ingredient);

    boolean deleteIngredient(long id);

    Map<Long, Ingredient> getListOfAllIngredients();
}
