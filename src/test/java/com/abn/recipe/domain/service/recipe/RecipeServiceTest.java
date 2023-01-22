package com.abn.recipe.domain.service.recipe;

import com.abn.recipe.common.exception.ABNServiceException;
import com.abn.recipe.domain.model.Ingredient;
import com.abn.recipe.domain.model.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RecipeServiceTest {

    RecipeRepository recipeRepository = mock(RecipeRepository.class);

    @Test
    void test_save_is_working() {

        when(recipeRepository.save(any())).thenReturn(1);
        RecipeService recipeService = new RecipeService(recipeRepository);
        Recipe recipe = new Recipe();
        recipe.setName("Pizza");
        Set<Ingredient> ingredientSet = new HashSet<>();
        ingredientSet.add(new Ingredient("Bread"));
        recipe.setIngredients(ingredientSet);
        long id = recipeService.save(recipe);
        Assertions.assertEquals(1, id);
    }

    @Test
    void test_update_is_working() {
        when(recipeRepository.update(any())).thenReturn(1);
        RecipeService recipeService = new RecipeService(recipeRepository);
        Recipe recipe = new Recipe();
        recipe.setName("Pizza");
        recipe.setId(1);
        long id = recipeService.update(recipe);
        Assertions.assertEquals(1, id);
    }

    @Test
    void test_update_id_not_sent() {
        when(recipeRepository.update(any())).thenReturn(1);
        RecipeService recipeService = new RecipeService(recipeRepository);
        Recipe recipe = new Recipe();
        recipe.setName("Pizza");
        Assertions.assertThrows(ABNServiceException.class, () -> recipeService.update(recipe));
    }

    @Test
    void test_update_id_not_found() {
        when(recipeRepository.update(any())).thenReturn(-1);
        RecipeService recipeService = new RecipeService(recipeRepository);
        Recipe recipe = new Recipe();
        recipe.setName("Pizza");
        recipe.setId(2);
        Assertions.assertThrows(ABNServiceException.class, () -> recipeService.update(recipe));
    }

    @Test
    void test_getById_is_working() {
        Recipe recipe = new Recipe();
        recipe.setName("Pizza");
        recipe.setId(1);
        when(recipeRepository.get(1)).thenReturn(Optional.of(recipe));
        RecipeService recipeService = new RecipeService(recipeRepository);

        Recipe byId = recipeService.getById(1);
        Assertions.assertEquals(1, byId.getId());
    }

    @Test
    void test_delete_is_working() {
        RecipeService recipeService = new RecipeService(recipeRepository);
         recipeService.delete(1);
        verify(recipeRepository, times(1)).delete(1);
    }



}