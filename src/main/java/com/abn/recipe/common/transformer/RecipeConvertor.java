package com.abn.recipe.common.transformer;

import com.abn.recipe.api.model.recipe.RecipeRequestDTO;
import com.abn.recipe.api.model.recipe.RecipeResponseDTO;
import com.abn.recipe.api.model.recipe.RecipeTypeEnum;
import com.abn.recipe.domain.model.Ingredient;
import com.abn.recipe.domain.model.Recipe;
import com.abn.recipe.repository.ingredient.IngredientEntity;
import com.abn.recipe.repository.recipe.RecipeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A transfomrer responsible for the conversion of Recipe
 */
@Mapper
public interface RecipeConvertor {
    RecipeConvertor INSTANCE = Mappers.getMapper(RecipeConvertor.class);

    RecipeEntity recipeToEntity(Recipe recipe);
   default RecipeEntity recipeToEntity(Recipe recipe, RecipeEntity recipeEntity){
       recipeEntity.setInstruction(recipe.getInstruction());
       recipeEntity.setType(recipe.getType());
       recipeEntity.setName(recipe.getName());
       recipeEntity.setNumberOfServings(recipe.getNumberOfServings());
        return recipeEntity;
    }

    Recipe entityToRecipe(RecipeEntity entity);

    default RecipeResponseDTO recipeToDTO(Recipe recipe) {
        return new RecipeResponseDTO(recipe.getName(), recipe.getNumberOfServings(),
                recipe.getIngredients().stream().map(Ingredient::getIngredient)
                        .collect(Collectors.joining(",")), recipe.getInstruction(),
                RecipeTypeEnum.getByValue(recipe.getType()));
    }

    List<Recipe> recipeEntitiesToRecipes(List<RecipeEntity> recipe);

    default List<RecipeResponseDTO> recipesToDTOs(List<Recipe> recipes) {
        return recipes.stream()
                .map(recipe -> new RecipeResponseDTO(recipe.getName(), recipe.getNumberOfServings(),
                        recipe.getIngredients().stream().map(Ingredient::getIngredient).collect(
                                Collectors.joining(",")), recipe.getInstruction(),
                        RecipeTypeEnum.getByValue(recipe.getType())))
                .collect(Collectors.toList());
    }

    default Recipe dtoToRecipe(RecipeRequestDTO recipeRequestDTO) {
        Recipe recipe = new Recipe();
        Set<Ingredient> ingredients = new HashSet<>();
        for (Integer id : recipeRequestDTO.getIngredientIds()) {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(id);
            ingredients.add(ingredient);
        }
        recipe.setIngredients(ingredients);
        recipe.setInstruction(recipeRequestDTO.getInstruction());
        recipe.setType(recipeRequestDTO.getType().name());
        recipe.setNumberOfServings(recipeRequestDTO.getNumberOfServings());
        recipe.setName(recipeRequestDTO.getName());
        recipe.setId(recipeRequestDTO.getId());
        return recipe;
    }
}
