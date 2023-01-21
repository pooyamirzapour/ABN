package com.abn.recipe.domain.service.ingredient;

import com.abn.recipe.domain.model.Ingredient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Any;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IngredientServiceTest {


    IngredientRepository ingredientRepository = mock(IngredientRepository.class);
    @Test
    void test_save_is_working() {
        when(ingredientRepository.save(any())).thenReturn(1);
        IngredientService ingredientService = new IngredientService(ingredientRepository);
        Integer saveId = ingredientService.save(new Ingredient("salt"));
        Assertions.assertEquals(1, saveId);
    }

    @Test
    void test_delete_is_working() {
        IngredientService ingredientService = new IngredientService(ingredientRepository);
        ingredientService.delete(1);
        verify(ingredientRepository, times(1)).delete(1);

    }

    @Test
    void test_getAll_is_working() {
        HashSet<Ingredient> hashSet = new HashSet<>();
        hashSet.add(new Ingredient("salt"));
        when(ingredientRepository.findAll()).thenReturn(hashSet);
        IngredientService ingredientService = new IngredientService(ingredientRepository);
        Set<Ingredient> set = ingredientService.getAll();
        Assertions.assertEquals(1, set.size());
    }

    @Test
    void test_getById_is_working() {
        Ingredient ing = new Ingredient("salt");
        when(ingredientRepository.findById(1)).thenReturn(ing);
        IngredientService ingredientService = new IngredientService(ingredientRepository);
        Ingredient ingredient = ingredientService.getById(1);
        Assertions.assertEquals(ing.getIngredient(), ingredient.getIngredient());
    }

}