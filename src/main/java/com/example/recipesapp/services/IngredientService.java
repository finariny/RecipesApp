package com.example.recipesapp.services;

import com.example.recipesapp.model.Ingredient;

import java.util.Map;

/**
 * Сервис для работы с ингредиентами
 */

public interface IngredientService {

    /**
     * Сохранить ингредиент
     * @param ingredient - ингредиент для сохранения
     * @return - сохраненный ингредиент
     */
    Ingredient addIngredient(Ingredient ingredient);

    /**
     * Получить ингредиент по ID
     * @param id - ID ингредиента
     * @return - ингредиент
     */
    Ingredient getIngredient(long id);

    /**
     * Редактировать ингредиент
     * @param id - ID ингредиента
     * @param ingredient - ингредиент
     * @return - обновленный ингредиент
     */
    Ingredient editIngredient(long id, Ingredient ingredient);

    /**
     * Удалить ингредиент
     * @param id - ID ингредиента
     * @return - true (ингредиент удален) / false (ингредиент не удален)
     */
    boolean deleteIngredient(long id);

    /**
     * Получить список всех ингредиентов
     * @return - спиок ингредиентов
     */
    Map<Long, Ingredient> getListOfAllIngredients();
}
