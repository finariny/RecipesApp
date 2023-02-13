package com.example.recipesapp.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@EqualsAndHashCode
@Getter
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
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Название ингредиента введено неверно");
        } else {
            this.name = name;
        }
    }

    public void setCount(int count){
        if (count < 0) {
            throw new IllegalArgumentException("Количество ингредиентов введено неверно");
        } else {
            this.count = count;
        }
    }

    public void setUnit(String unit){
        if (StringUtils.isBlank(unit)) {
            throw new IllegalArgumentException("Единица измерения ингредиентов введена неверно");
        } else {
            this.unit = unit;
        }
    }

    @Override
    public String toString() {
        return "Название ингредиента: " + this.name + "; Количество ингредиентов: " + this.count +
                "; Единица измерения ингредиентов: " + this.unit;
    }
}
