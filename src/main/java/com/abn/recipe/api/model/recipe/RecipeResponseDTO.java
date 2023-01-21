package com.abn.recipe.api.model.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

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
