package com.example.recipesapp.services;

import com.example.recipesapp.model.Recipe;

import java.io.File;
import java.util.Map;

/**
 * Сервис для работы с рецептами
 */

public interface RecipeService {

    /**
     * Сохранить рецепт
     * @param recipe - рецепт для сохранения
     * @return - сохраненный рецепт
     */
    Recipe addRecipe(Recipe recipe);

    /**
     * Получить рецепт по ID
     * @param id - ID рецепт
     * @return - рецепт
     */
    Recipe getRecipe(long id);

    /**
     * Редактировать рецепт
     * @param id - ID рецепта
     * @param recipe - рецепт
     * @return - обновленный рецепт
     */
    Recipe editRecipe(long id, Recipe recipe);

    /**
     * Удалить рецепт
     * @param id - ID рецепта
     * @return - true (рецепт удален) / false (рецепт не удален)
     */
    boolean deleteRecipe(long id);

    /**
     * Получить список всех рецептов
     * @return - спиок рецептов
     */
    Map<Long, Recipe> getListOfAllRecipes();

    /**
     * Получить список всех отформатированных рецептов в виде строки
     * @return - строка с рецептами
     */
    String getFormattedRecipesToString();
}
