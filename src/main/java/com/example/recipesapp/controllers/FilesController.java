package com.example.recipesapp.controllers;

import com.example.recipesapp.services.FilesService;
import com.example.recipesapp.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;

@RestController
@RequestMapping("/files")
@Tag(
        name = "Файлы",
        description = "CRUD-операции для работы с файлами"
)
public class FilesController {

    @Value("${path.to.recipes.file}")
    private String recipesFilePath;

    @Value("${name.of.recipes.file}")
    private String recipesFileName;

    @Value("${name.of.formatted.recipes.file}")
    private String formattedRecipesFileName;

    @Value("${path.to.ingredients.file}")
    private String ingredientsFilePath;

    @Value("${name.of.ingredients.file}")
    private String ingredientsFileName;

    private final FilesService filesService;
    private final RecipeService recipeService;

    public FilesController(FilesService filesService, RecipeService recipeService) {
        this.filesService = filesService;
        this.recipeService = recipeService;
    }

    @GetMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Скачать все рецепты в виде json-файла"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл доступен для скачивания"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Файл пуст"
            )
    })
    public ResponseEntity<InputStreamResource> downloadRecipesFile() throws FileNotFoundException {
        File file = filesService.getFile(recipesFilePath, formattedRecipesFileName);
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipesLog.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/export/formatted", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Скачать файл со всеми отформатированными рецептами"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл доступен для скачивания"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Файл пуст"
            )
    })
    public ResponseEntity<InputStreamResource> downloadFormattedRecipesFile() throws FileNotFoundException {
        filesService.saveToFile(recipeService.getFormattedRecipesToString(), Path.of(recipesFilePath, formattedRecipesFileName));
        File file = filesService.getFile(recipesFilePath, formattedRecipesFileName);
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"FormattedRecipesLog.txt\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/import/recipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузить json-файл с рецептами"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл загружен"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла внутренняя ошибка сервера"
            )
    })
    public ResponseEntity<Void> uploadRecipesFile(@RequestParam MultipartFile multipartFile) {
        if (uploadFile(multipartFile, recipesFilePath, recipesFileName)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/import/ingredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузить json-файл с ингредиентами"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл загружен"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла внутренняя ошибка сервера"
            )
    })
    public ResponseEntity<Void> uploadIngredientsFile(@RequestParam MultipartFile multipartFile) {
        if (uploadFile(multipartFile, ingredientsFilePath, ingredientsFileName)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private boolean uploadFile(MultipartFile multipartFile, String filePath, String fileName) {
        filesService.cleanFile(Path.of(filePath, fileName));
        File file = filesService.getFile(filePath, fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            IOUtils.copy(multipartFile.getInputStream(), fos);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
