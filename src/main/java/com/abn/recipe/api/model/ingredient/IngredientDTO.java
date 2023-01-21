package com.abn.recipe.api.model.ingredient;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDTO {
    public static final String INGREDIENT_IS_REQUIRED = "Ingredient is required";

    @NotBlank(message = INGREDIENT_IS_REQUIRED)
    @NotNull(message = INGREDIENT_IS_REQUIRED)
    @ApiModelProperty(notes = "Name of ingredient", example = "Salt")
    private String ingredient;

}
