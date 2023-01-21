package com.abn.recipe.repository.ingredient;

import com.abn.recipe.common.exception.ABNServiceException;
import com.abn.recipe.common.exception.ErrorCode;
import com.abn.recipe.common.transformer.IngredientConverter;
import com.abn.recipe.domain.model.Ingredient;
import com.abn.recipe.domain.service.ingredient.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class IngredientRepositoryImpl implements IngredientRepository {

    private final IngredientJpaRepository ingredientJpaRepository;

    @Override
    public Integer save(Ingredient ingredient) {
        IngredientEntity save =
                ingredientJpaRepository.save(IngredientConverter.INSTANCE.ingredientToEntity(ingredient));
        return save.getId();
    }

    @Override
    public void delete(int id) {
        ingredientJpaRepository.deleteById(id);
    }

    @Override
    public Set<Ingredient> findAll() {
        return IngredientConverter.INSTANCE.iterableEntityToSet(ingredientJpaRepository.findAll());

    }

    @Override
    public Ingredient findById(int id) {
        Optional<IngredientEntity> found = ingredientJpaRepository.findById(id);
        if (found.isPresent()) {
            return IngredientConverter.INSTANCE.entityToIngredient(found.get());
        }
        throw new ABNServiceException("Ingredient not found", ErrorCode.INGREDIENT_NOT_FOUND, HttpStatus.NOT_FOUND);

    }
}
