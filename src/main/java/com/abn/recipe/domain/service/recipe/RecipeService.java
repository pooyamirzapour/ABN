package com.abn.recipe.domain.service.recipe;

import com.abn.recipe.api.model.recipe.RecipeResponseDTO;
import com.abn.recipe.common.exception.ABNServiceException;
import com.abn.recipe.common.exception.ErrorCode;
import com.abn.recipe.common.transformer.RecipeConvertor;
import com.abn.recipe.domain.model.Recipe;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
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
            throw new ABNServiceException("id not sent", ErrorCode.ID_NOT_SENT, HttpStatus.BAD_REQUEST);
        }

        int update = recipeRepository.update(recipe);
        if (update == -1) {
            throw new ABNServiceException("id is not sent", ErrorCode.RECIPE_NOT_FOUND,
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return update;
    }

    public Recipe getById(int id) {
        Optional<Recipe> recipe = recipeRepository.get(id);
        if (recipe.isEmpty()) {
            throw new ABNServiceException("recipe not found", ErrorCode.RECIPE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return recipe.get();
    }

    public List<RecipeResponseDTO> search(Map<String, String[]> allRequestParams, int page, int size) {
        String[] include = allRequestParams.get("include");
        String[] exclude = allRequestParams.get("exclude");
        String[] noServings = allRequestParams.get("noserving");
        String[] type = allRequestParams.get("type");
        String[] instruction = allRequestParams.get("instruction");

        return RecipeConvertor.INSTANCE.recipesToDTOs(recipeRepository.query(include,exclude,instruction,noServings,type));

    }
}
