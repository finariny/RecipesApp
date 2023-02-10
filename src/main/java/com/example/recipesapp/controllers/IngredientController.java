package com.example.recipesapp.controllers;

import com.example.recipesapp.model.Ingredient;
import com.example.recipesapp.services.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController (IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<Long> addIngredient(@RequestBody Ingredient ingredient) {
        long id = ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable long id) {
        Ingredient ingredient = ingredientService.getIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(ingredient);
        }
    }
}
