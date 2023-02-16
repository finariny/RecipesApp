package com.example.recipesapp.services.impl;

import com.example.recipesapp.services.FilesService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {

    @Override
    public boolean saveToFile(String json, Path path) {
        try {
            cleanFile(path);
            Files.writeString(path, json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFile(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public File getFile(String filePath, String fileName) {
        return new File(filePath + "/" + fileName);
    }

    @Override
    public boolean cleanFile(Path path) {
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
