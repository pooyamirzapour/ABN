package com.abn.recipe.domain.service.recipe;

import com.abn.recipe.domain.model.Recipe;
import com.abn.recipe.domain.model.SearchQueryServiceRequest;
import com.abn.recipe.domain.model.SearchQueryServiceResponse;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository {

    int save(Recipe recipe);

    int update(Recipe recipe);

    Optional<Recipe> get(int id);

    void delete(int id);

    List<SearchQueryServiceResponse> query(SearchQueryServiceRequest req, List<Integer> excludes, List<Integer> includes);

     List<Integer> findByIngredientIn(List<String> req);
}
