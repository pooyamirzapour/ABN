package com.abn.recipe.repository.recipe;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeJpaRepository extends CrudRepository<RecipeEntity, Integer> {

//    @Query(value = "select rec.* from recipe rec innjer join recipe_ingredient rec_ing on rec.identity=ewc_ing.recipe_id inner join ingredient ing on ing.id=rec_ing.ingredient_id ", nativeQuery = true)
//    List<RecipeEntity> findByIngredientsInAndIngredientsNotInAndInstrAndInstructionContainsAndNumberOfServingsAndTypeAnd(
//            List<String> includes, List<String> excludes, String instruction, int noServing, String type);

    @Query(value =
            "select recipe.* from recipe inner join recipe_ingredient on recipe.id = recipe_ingredient.recipe_id inner join ingredients on ingredients.id = ingredient_id  where ingredient in (?1) and ingredients.ingredient not in (?2)"
                    + " and recipe.instruction like LOWER(CONCAT('%', ?3,'%')) and number_of_servings = ?4 and type=?5", nativeQuery = true)
    List<RecipeEntity> query(List<String> includes, List<String> excludes, String instruction, int noServing,
            String type);

}
