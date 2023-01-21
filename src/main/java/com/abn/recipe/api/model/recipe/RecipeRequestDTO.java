package com.abn.recipe.api.model.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequestDTO {
    public static final String NAME_IS_REQUIRED = "name is required";
    public static final String NUMBER_OF_SERVINGS_IS_REQUIRED = "numberOfServings is required";
    public static final String INGREDIENT_IDS_IS_REQUIRED = "ingredientIds is required";
    public static final String INSTRUCTION_IS_REQUIRED = "instruction is required";
    public static final String TYPE_IS_REQUIRED = "type is required";

    private int id;

    @NotBlank(message = NAME_IS_REQUIRED)
    @NotNull(message = NAME_IS_REQUIRED)
    private String name;

    @NotEmpty(message = NUMBER_OF_SERVINGS_IS_REQUIRED)
    @NotNull(message = NUMBER_OF_SERVINGS_IS_REQUIRED)
    private int numberOfServings;

    @NotBlank(message = INGREDIENT_IDS_IS_REQUIRED)
    @NotNull(message = INGREDIENT_IDS_IS_REQUIRED)
    private Set<Integer> ingredientIds;

    @NotBlank(message = INSTRUCTION_IS_REQUIRED)
    @NotNull(message = INSTRUCTION_IS_REQUIRED)
    private String instruction;

    @NotBlank(message = TYPE_IS_REQUIRED)
    @NotNull(message = TYPE_IS_REQUIRED)
    private RecipeTypeEnum type;

    public RecipeRequestDTO(String name, int numberOfServings, Set<Integer> ingredientIds, String instruction,
            RecipeTypeEnum type) {
        this.name = name;
        this.numberOfServings = numberOfServings;
        this.ingredientIds = ingredientIds;
        this.instruction = instruction;
        this.type = type;
    }
}
