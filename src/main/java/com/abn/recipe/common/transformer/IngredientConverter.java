package com.abn.recipe.common.transformer;

import com.abn.recipe.api.model.ingredient.IngredientDTO;
import com.abn.recipe.domain.model.Ingredient;
import com.abn.recipe.repository.ingredient.IngredientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

/**
 * A transformer responsible for the conversion of Ingredient
 */
@Mapper
public interface IngredientConverter {
    IngredientConverter INSTANCE = Mappers.getMapper(IngredientConverter.class);

    Ingredient dtoToIngredient(IngredientDTO recipeRequestDTO);

    IngredientDTO ingredientToDTO(Ingredient ingredient);

    Set<IngredientDTO> ingredientsToDTOs(Set<Ingredient> ingredient);
    Ingredient entityToIngredient(IngredientEntity ingredient);

    IngredientEntity ingredientToEntity(Ingredient ingredient);

    default Set<Ingredient> iterableEntityToSet(Iterable<IngredientEntity> entities) {
        Set<Ingredient> set = new HashSet<>();
        entities.forEach(f -> {
            set.add(new Ingredient(f.getIngredient()));
        });
        return set;
    }

}
