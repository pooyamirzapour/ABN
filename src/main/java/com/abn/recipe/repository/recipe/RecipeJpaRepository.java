package com.abn.recipe.repository.recipe;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeJpaRepository extends CrudRepository<RecipeEntity, Integer> {

    /**
     * Fetch data based on criteria.
     *
     * @param instruction - text
     * @param noServing   - number of serving
     * @param type        - recipe type
     * @return - search result
     */

    @Query(value =
            " select finaltbl.name ,  finaltbl.type, finaltbl.number_of_servings as no   , GROUP_CONCAT(ingredient) as ingredient , finaltbl.instruction "
                    + " from(  select distinct mytbl.instruction, mytbl.name, mytbl.recipe_id , mytbl.type , mytbl.Number_of_servings, recipe_ingredient.ingredient_id from (select distinct * from "
                    + " (select distinct * from  recipe_ingredient as a where recipe_id  not in ( select  recipe_id from recipe_ingredient where ingredient_id in (:exclusion) or :exclusion is null )"
                    + " and  ingredient_id in (:inclusion) or :inclusion is null) tbl inner join recipe on recipe.id =tbl.recipe_id ) mytbl"
                    + " inner join recipe_ingredient on mytbl.recipe_id =recipe_ingredient.recipe_id ) finaltbl"
                    + " inner join ingredients on ingredients.id= finaltbl.ingredient_id where (finaltbl.instruction= :instruction or :instruction is null ) "
                    + " and  (finaltbl.number_of_servings= :noServing or :noServing is null ) and  (finaltbl.type= :type or :type is null )"
                    + " group by  finaltbl.name , finaltbl.type, finaltbl.number_of_servings,finaltbl.instruction "
            , nativeQuery = true)
    List<SearchQueryEntityModel> query(@Param("instruction") String instruction, @Param("noServing") Integer noServing,
            @Param("type") String type, @Param("exclusion") List<Integer> exclusion,
            @Param("inclusion") List<Integer> inclusion , Pageable pageable);


}



