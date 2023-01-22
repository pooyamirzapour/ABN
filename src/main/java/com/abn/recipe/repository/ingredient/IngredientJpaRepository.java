package com.abn.recipe.repository.ingredient;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientJpaRepository extends CrudRepository<IngredientEntity, Integer> {

    List<IngredientEntity> findByIngredientIn(List<String> names);

}
