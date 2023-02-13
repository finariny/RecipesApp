package com.example.recipesapp.controllers;

import com.example.recipesapp.model.Recipe;
import com.example.recipesapp.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")
@Tag(
        name = "Рецепты",
        description = "CRUD-операции для работы с рецептами"
)
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    @Operation(
            summary = "Добавление рецепта"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт добавлен"
            )
    })
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        return ResponseEntity.ok(recipeService.addRecipe(recipe));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение рецепта по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт найден"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не найден"
            )
    })
    public ResponseEntity<Recipe> getRecipeById(@PathVariable long id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(recipe);
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Редактирование рецепта по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт отредактирован"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не найден или структура рецепта написана неверно"
            )
    })
    public ResponseEntity<Recipe> editRecipe (@PathVariable long id, @RequestBody Recipe newRecipe) {
        Recipe recipe = recipeService.editRecipe(id, newRecipe);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(recipe);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление рецепта по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт удалён"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не найден"
            )
    })
    public ResponseEntity<Void> deleteRecipe (@PathVariable long id) {
        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(
            summary = "Получение списка всех рецептов"
    )
    public ResponseEntity<Map<Long, Recipe>> getListOfAllRecipes() {
        return ResponseEntity.ok(recipeService.getListOfAllRecipes());
    }
}
