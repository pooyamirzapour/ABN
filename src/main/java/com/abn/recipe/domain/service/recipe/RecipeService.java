package com.abn.recipe.domain.service.recipe;

import com.abn.recipe.common.exception.ABNServiceException;
import com.abn.recipe.common.exception.ErrorCode;
import com.abn.recipe.domain.model.Recipe;
import com.abn.recipe.domain.model.SearchQueryServiceRequest;
import com.abn.recipe.domain.model.SearchQueryServiceResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Basic crud operations
 */
@Service
@AllArgsConstructor
@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public int save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void delete(int id) {
        recipeRepository.delete(id);
    }

    public int update(Recipe recipe) {
        if (recipe.getId() == 0) {
            log.error("id not sent");
            throw new ABNServiceException("id not sent", ErrorCode.ID_NOT_SENT, HttpStatus.BAD_REQUEST);
        }

        int update = recipeRepository.update(recipe);
        if (update == -1) {
            log.error("id not sent");
            throw new ABNServiceException("id is not sent", ErrorCode.RECIPE_NOT_FOUND,
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return update;
    }

    public Recipe getById(int id) {
        Optional<Recipe> recipe = recipeRepository.get(id);
        if (recipe.isEmpty()) {
            log.error("recipe not found");
            throw new ABNServiceException("recipe not found", ErrorCode.RECIPE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return recipe.get();
    }

    public List<SearchQueryServiceResponse> search(SearchQueryServiceRequest searchQueryServiceRequest) {
        List<Integer> excludes = ingredientsToIds(searchQueryServiceRequest.getExcludes());
        List<Integer> includes = ingredientsToIds(searchQueryServiceRequest.getIncludes());
        return recipeRepository.query(searchQueryServiceRequest, excludes, includes);
    }

    private List<Integer> ingredientsToIds(List<String> req) {
        List<Integer> Ids = null;
        if (req != null) {
            Ids = recipeRepository.findByIngredientIn(req);

            if (req.size() != Ids.size() || Ids.size() == 0) {
                log.error("ingredient not found");
                throw new ABNServiceException("ingredient not found", ErrorCode.INGREDIENT_NOT_FOUND,
                        HttpStatus.NOT_FOUND);
            }
        }
        return Ids;
    }

}
