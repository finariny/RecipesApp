package com.example.recipesapp.model;

import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
public class Recipe {
    private String name;
    private int cookingTimeInMinutes;
    private List<Ingredient> ingredientList;
    private List<String> cookingSteps;

    public Recipe(String name, int cookingTimeInMinutes, List<Ingredient> ingredientList, List<String> cookingSteps) {
        setName(name);
        setCookingTimeInMinutes(cookingTimeInMinutes);
        setIngredientList(ingredientList);
        setCookingSteps(cookingSteps);
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название рецепта введено неверно");
        } else {
            this.name = name;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setCookingTimeInMinutes(int cookingTimeInMinutes) {
        if (cookingTimeInMinutes < 1) {
            throw new IllegalArgumentException("Время приготовления введено неверно");
        } else {
            this.cookingTimeInMinutes = cookingTimeInMinutes;
        }
    }

    public int getCookingTimeInMinutes() {
        return this.cookingTimeInMinutes;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        if (ingredientList.size() < 1) {
            throw new IllegalArgumentException("В рецепте должны быть указаны ингредиенты");
        } else {
            this.ingredientList = ingredientList;
        }
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setCookingSteps(List<String> cookingSteps) {
        if (cookingSteps.size() < 1) {
            throw new IllegalArgumentException("В рецепте должны быть указаны шаги приготовления");
        } else {
            this.cookingSteps = cookingSteps;
        }
    }

    public List<String> getCookingSteps() {
        return cookingSteps;
    }

    @Override
    public String toString() {
        return "Название рецепта: " + this.name + "; Время приготовления в минутах: " + this.cookingTimeInMinutes +
                "; Список ингредиентов: " + this.ingredientList + "; Шаги приготовления: " + this.cookingSteps;
    }
}
