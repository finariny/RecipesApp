package com.example.recipesapp.services;

public interface FilesService {
    boolean saveToRecipesFile(String json);

    boolean saveToIngredientsFile(String json);

    String readFromRecipesFile();

    String readFromIngredientsFile();
}
