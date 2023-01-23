package com.abn.recipe.repository.recipe;

import com.abn.recipe.common.exception.ABNServiceException;
import com.abn.recipe.common.exception.ErrorCode;
import com.abn.recipe.common.transformer.RecipeConvertor;
import com.abn.recipe.common.transformer.SearchConverter;
import com.abn.recipe.domain.model.Recipe;
import com.abn.recipe.domain.model.SearchQueryServiceRequest;
import com.abn.recipe.domain.model.SearchQueryServiceResponse;
import com.abn.recipe.domain.service.recipe.RecipeRepository;
import com.abn.recipe.repository.ingredient.IngredientEntity;
import com.abn.recipe.repository.ingredient.IngredientJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class RecipeRepositoryImpl implements RecipeRepository {

    private final RecipeJpaRepository recipeJpaRepository;
    private final IngredientJpaRepository ingredientJpaRepository;

    public IngredientEntity findById(int id) {
        return ingredientJpaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("ingredient not found");
                    return new ABNServiceException("ingredient not found", ErrorCode.INGREDIENT_NOT_FOUND,
                            HttpStatus.NOT_FOUND);
                });
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

    /**
     * First we extract Ids based on names, then call the query.
     * @param req - search params
     * @param excludes - ingredients which should be excluded
     * @param includes - ingredients which should be included
     * @return result list
     */
    @Override
    public List<SearchQueryServiceResponse> query(SearchQueryServiceRequest req, List<Integer> excludes, List<Integer> includes) {


        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());

        List<SearchQueryEntityModel> query;
        if (excludes == null || excludes.size() == 0) {
            query =
                    recipeJpaRepository.queryWithoutExclusion(req.getInstruction(), req.getNoServings(), req.getType(),
                            includes, pageable);
        } else {
            query =
                    recipeJpaRepository.query(req.getInstruction(), req.getNoServings(), req.getType(), excludes,
                            includes,
                            pageable);
        }
        return SearchConverter.INSTANCE.queryEntityToQueryService(query);

    }

    @Override
    public List<Integer> findByIngredientIn(List<String> req) {
        List<Integer> Ids = null;
        if (req != null) {
            Ids = ingredientJpaRepository.findByIngredientIn(req)
                    .stream()
                    .map(IngredientEntity::getId).collect(Collectors.toList());
            if (req.size() != Ids.size() || Ids.size() == 0) {
                log.error("ingredient not found");
                throw new ABNServiceException("ingredient not found", ErrorCode.INGREDIENT_NOT_FOUND,
                        HttpStatus.NOT_FOUND);
            }
        }
        return Ids;
    }



}
