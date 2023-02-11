package com.example.recipesapp.controllers;

import com.example.recipesapp.model.Ingredient;
import com.example.recipesapp.services.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController (IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<String> addIngredient(@RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(ingredientService.addIngredient(ingredient));
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

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> editIngredient (@PathVariable long id, @RequestBody Ingredient newIngredient) {
        Ingredient ingredient = ingredientService.editIngredient(id, newIngredient);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(ingredient);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient (@PathVariable long id) {
        if (ingredientService.deleteIngredient(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Map<Long, Ingredient>> getListOfAllIngredients() {
        return ResponseEntity.ok(ingredientService.getListOfAllIngredients());
    }
}
