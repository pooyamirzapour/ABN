package com.abn.recipe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    private String ingredient;
    private int id;

    public Ingredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
