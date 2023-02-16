package com.example.recipesapp.services;

import java.io.File;
import java.nio.file.Path;

public interface FilesService {
    boolean saveToFile(String json, Path path);

    String readFromFile(Path path);

    File getFile(String filePath, String fileName);

    boolean cleanFile(Path path);
}
