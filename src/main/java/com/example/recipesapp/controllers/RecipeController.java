package com.example.recipesapp.controllers;

import com.example.recipesapp.model.Recipe;
import com.example.recipesapp.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<Long> addRecipe(@RequestBody Recipe recipe) {
        long id = recipeService.addRecipe(recipe);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable long id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(recipe);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> editRecipe (@PathVariable long id, @RequestBody Recipe newRecipe) {
        Recipe recipe = recipeService.editRecipe(id, newRecipe);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(recipe);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe (@PathVariable long id) {
        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Map<Long, Recipe>> getListOfAllRecipes() {
        return ResponseEntity.ok(recipeService.getListOfAllRecipes());
    }
}
