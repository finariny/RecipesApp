package com.example.recipesapp.controllers;

import com.example.recipesapp.model.Ingredient;
import com.example.recipesapp.services.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredient")
@Tag(
        name = "Ингредиенты",
        description = "CRUD-операции для работы с ингредиентами"
)
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController (IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    @Operation(
            summary = "Добавление ингредиента"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент добавлен"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Такой ингредиент уже существует!"
            )
    })
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(ingredientService.addIngredient(ingredient));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение информации об ингредиенте по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент найден"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент не найден"
            )
    })
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable long id) {
        Ingredient ingredient = ingredientService.getIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(ingredient);
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = " Редактирование ингредиента по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент отредактирован"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент не найден или структура ингредиента написана неверно"
            )
    })
    public ResponseEntity<Ingredient> editIngredient (@PathVariable long id, @RequestBody Ingredient newIngredient) {
        Ingredient ingredient = ingredientService.editIngredient(id, newIngredient);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(ingredient);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление ингредиента по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент удалён"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент не найден"
            )
    })
    public ResponseEntity<Void> deleteIngredient (@PathVariable long id) {
        if (ingredientService.deleteIngredient(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(
            summary = "Получение полного списка ингредиентов"
    )
    public ResponseEntity<Map<Long, Ingredient>> getListOfAllIngredients() {
        return ResponseEntity.ok(ingredientService.getListOfAllIngredients());
    }
}
