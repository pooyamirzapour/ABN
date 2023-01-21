package com.abn.recipe.domain.service.ingredient;

import com.abn.recipe.domain.model.Ingredient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public Integer save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public void delete(int id) {
        ingredientRepository.delete(id);
    }

    public Set<Ingredient> getAll() {
       return ingredientRepository.findAll();
    }

    public Ingredient getById(int id) {
        return ingredientRepository.findById(id);
    }

}
