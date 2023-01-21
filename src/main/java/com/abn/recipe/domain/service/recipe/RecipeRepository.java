package com.abn.recipe.domain.service.recipe;

import com.abn.recipe.domain.model.Recipe;


import java.util.List;
import java.util.Optional;

public interface RecipeRepository {

    int save(Recipe recipe);

    int update(Recipe recipe);

    Optional<Recipe> get(int id);

    List<Recipe> query(String[] include, String[] exclude, String[] instruction, String[] noServings, String[] type);
}
