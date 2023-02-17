package com.example.recipesapp.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
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
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Название рецепта введено неверно");
        } else {
            this.name = name;
        }
    }

    public void setCookingTimeInMinutes(int cookingTimeInMinutes) {
        if (cookingTimeInMinutes < 1) {
            throw new IllegalArgumentException("Время приготовления введено неверно");
        } else {
            this.cookingTimeInMinutes = cookingTimeInMinutes;
        }
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        if (ingredientList.size() < 1) {
            throw new IllegalArgumentException("В рецепте должны быть указаны ингредиенты");
        } else {
            this.ingredientList = ingredientList;
        }
    }

    public void setCookingSteps(List<String> cookingSteps) {
        if (cookingSteps.size() < 1) {
            throw new IllegalArgumentException("В рецепте должны быть указаны шаги приготовления");
        } else {
            this.cookingSteps = cookingSteps;
        }
    }

    @Override
    public String toString() {
        return this.name + "\nВремя приготовления: " + this.cookingTimeInMinutes + " минут.\n";
    }
}
