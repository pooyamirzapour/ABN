package com.abn.recipe.api.model.ingredient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientResponseDTO {
    Set<IngredientDTO> ingredientDTOSet;
}
