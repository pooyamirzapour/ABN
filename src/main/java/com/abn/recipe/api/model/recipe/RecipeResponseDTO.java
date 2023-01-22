package com.abn.recipe.api.model.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecipeResponseDTO {

    private String name;

    private int numberOfServings;

    private String ingredients;

    private String instruction;

    private RecipeTypeEnum type;

}
