package com.example.recipesapp.services;

import java.nio.file.Path;

public interface FilesService {
    boolean saveToFile(String json, Path path);

    String readFromFile(Path path);
}
