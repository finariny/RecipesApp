package com.example.recipesapp.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Ingredient {
    private String name;
    private int count;
    private String unit;

    public Ingredient(String name, int count, String unit) {
        setName(name);
        setCount(count);
        setUnit(unit);
    }

    public void setName(String name){
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название ингредиента введено неверно");
        } else {
            this.name = name;
        }
    }

    public String getName(){
        return this.name;
    }

    public void setCount(int count){
        if (count < 0) {
            throw new IllegalArgumentException("Количество ингредиентов введено неверно");
        } else {
            this.count = count;
        }
    }

    public int getCount(){
        return this.count;
    }

    public void setUnit(String unit){
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("Единица измерения ингредиентов введена неверно");
        } else {
            this.unit = unit;
        }
    }

    public String getUnit() {
        return this.unit;
    }

    @Override
    public String toString() {
        return "Название ингредиента: " + this.name + "; Количество ингредиентов: " + this.count +
                "; Единица измерения ингредиентов: " + this.unit;
    }
}
