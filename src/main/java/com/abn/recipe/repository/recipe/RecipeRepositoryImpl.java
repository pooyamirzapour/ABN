package com.abn.recipe.repository.recipe;

import com.abn.recipe.common.transformer.RecipeConvertor;
import com.abn.recipe.domain.model.Recipe;
import com.abn.recipe.domain.service.recipe.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class RecipeRepositoryImpl implements RecipeRepository {

    private final RecipeJpaRepository recipeJpaRepository;

    @Override
    public int save(Recipe recipe) {
        RecipeEntity save = recipeJpaRepository.save(RecipeConvertor.INSTANCE.recipeToEntity(recipe));
        return save.getId();
    }

    @Override
    public int update(Recipe recipe) {
        Optional<RecipeEntity> recipeEntity = recipeJpaRepository.findById(recipe.getId());
        if (recipeEntity.isEmpty()) {
            return -1;
        }
        return recipeJpaRepository.save(RecipeConvertor.INSTANCE.recipeToEntity(recipe)).getId();
    }

    @Override
    public Optional<Recipe> get(int id) {
        return recipeJpaRepository.findById(id).map(RecipeConvertor.INSTANCE::entityToRecipe);
    }

    @Override
    public List<Recipe> query(String[] include, String[] exclude, String[] instruction, String[] noServings, String[] type) {
        return RecipeConvertor.INSTANCE.recipeEntitiesToRecipes(recipeJpaRepository.query(null, null, null, 1, null));
    }
}
