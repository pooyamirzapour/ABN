package com.abn.recipe.domain.service.ingredient;

import com.abn.recipe.domain.model.Ingredient;

import java.util.Set;

public interface IngredientRepository {
    Integer save(Ingredient ingredient);

    void delete(int id);

    Set<Ingredient> findAll();

    Ingredient findById(int id);
}
