package com.abn.recipe.repository.recipe;

import com.abn.recipe.common.exception.ABNServiceException;
import com.abn.recipe.common.exception.ErrorCode;
import com.abn.recipe.common.transformer.RecipeConvertor;
import com.abn.recipe.domain.model.Recipe;
import com.abn.recipe.domain.service.recipe.RecipeRepository;
import com.abn.recipe.repository.ingredient.IngredientEntity;
import com.abn.recipe.repository.ingredient.IngredientJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class RecipeRepositoryImpl implements RecipeRepository {

    private final RecipeJpaRepository recipeJpaRepository;
    private final IngredientJpaRepository ingredientJpaRepository;

    public IngredientEntity findById(int id) {
        return ingredientJpaRepository.findById(id)
                .orElseThrow(() -> new ABNServiceException("ingredient not found", ErrorCode.INGREDIENT_NOT_FOUND,
                        HttpStatus.NOT_FOUND));
    }

    @Override
    public int save(Recipe recipe) {
        RecipeEntity recipeEntity = RecipeConvertor.INSTANCE.recipeToEntity(recipe);
        for (IngredientEntity ingredient : recipeEntity.getIngredients()) {
            IngredientEntity byId = findById(ingredient.getId());
            ingredient.setIngredient(byId.getIngredient());

        }
        RecipeEntity save = recipeJpaRepository.save(recipeEntity);
        return save.getId();
    }

    @Override
    public int update(Recipe recipe) {
        Optional<RecipeEntity> optionalRecipeEntity = recipeJpaRepository.findById(recipe.getId());
        if (optionalRecipeEntity.isEmpty()) {
            return -1;
        }
        RecipeEntity recipeEntity = optionalRecipeEntity.get();

        recipeEntity = RecipeConvertor.INSTANCE.recipeToEntity(recipe, recipeEntity);

        return recipeJpaRepository.save(recipeEntity).getId();
    }

    @Override
    public Optional<Recipe> get(int id) {
        return recipeJpaRepository.findById(id).map(RecipeConvertor.INSTANCE::entityToRecipe);
    }

    @Override
    public void delete(int id) {
        recipeJpaRepository.deleteById(id);
    }

    @Override
    public List<Recipe> query(String[] include, String[] exclude, String[] instruction, String[] noServings,
            String[] type) {
        return RecipeConvertor.INSTANCE.recipeEntitiesToRecipes(
                recipeJpaRepository.query(null, null, null, 1, null));
    }
}
