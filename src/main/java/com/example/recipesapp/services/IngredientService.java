package com.example.recipesapp.services;

import com.example.recipesapp.model.Ingredient;

public interface IngredientService {
    long addIngredient(Ingredient ingredient);

    Ingredient getIngredient(long id);
}
