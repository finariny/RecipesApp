package com.example.recipesapp.services;

import java.io.File;
import java.nio.file.Path;

/**
 * Сервис для работы с файлами
 */

public interface FilesService {

    /**
     * Сохранить в файл
     * @param content - название файла
     * @param path - путь до файла
     * @return  - true (файл сохранен) / false (файл не сохранен)
     */
    boolean saveToFile(String content, Path path);

    /**
     * Прочитать из файла
     * @param path - путь до файла
     * @return - преобразованный в строку файл
     */
    String readFromFile(Path path);

    /**
     * Получить файл
     * @param filePath - путь до файла
     * @param fileName - название файла
     * @return - файл
     */
    File getFile(String filePath, String fileName);

    /**
     * Очистить файл
     * @param path - путь до файла
     * @return - true (файл очищен) / false (файл не очищен)
     */
    boolean cleanFile(Path path);
}
