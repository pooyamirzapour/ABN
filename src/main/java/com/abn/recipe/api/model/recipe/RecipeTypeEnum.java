package com.abn.recipe.api.model.recipe;

import java.util.Arrays;

/**
 * Recipe types
 */
public enum RecipeTypeEnum {
    VEGETARIAN("vegetarian"),
    VEGAN("vegan"),
    OTHER("other");

    private String message;

    RecipeTypeEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static RecipeTypeEnum getByValue(String value) {
        return Arrays.stream(RecipeTypeEnum.values())
                .filter(type -> type.getMessage().equals(value))
                .findFirst()
                .orElse(RecipeTypeEnum.OTHER);

    }
}
