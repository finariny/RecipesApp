package com.example.recipesapp.controllers;

import com.example.recipesapp.services.FilesService;
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

    @Value("${path.to.ingredients.file}")
    private String ingredientsFilePath;

    @Value("${name.of.ingredients.file}")
    private String ingredientsFileName;
    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
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
        File file = filesService.getFile(recipesFilePath, recipesFileName);
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
    @PostMapping(value = "/import/recipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipesFile(@RequestParam MultipartFile file) {
        filesService.cleanFile(Path.of(recipesFilePath, recipesFileName));
        File recipesFile = filesService.getFile(recipesFilePath, recipesFileName);
        try (FileOutputStream fos = new FileOutputStream(recipesFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

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
    @PostMapping(value = "/import/ingredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadIngredientsFile(@RequestParam MultipartFile file) {
        filesService.cleanFile(Path.of(ingredientsFilePath, ingredientsFileName));
        File ingredientsFile = filesService.getFile(ingredientsFilePath, ingredientsFileName);
        try (FileOutputStream fos = new FileOutputStream(ingredientsFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
