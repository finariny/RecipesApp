package com.example.recipesapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @GetMapping("/")
    public String applicationLaunch() {
        return "Приложение запущено";
    }

    @GetMapping("/info")
    public String info() {
        return "Имя: Анастасия;<br>Название проекта: RecipesApp;<br>Дата создания: 04.02.2023;<br>Описание: веб-приложение для сайта рецептов.";
    }
}
