package com.abn.recipe.api.controller;

import com.abn.recipe.api.model.ingredient.IngredientDTO;
import com.abn.recipe.api.model.ingredient.IngredientResponseDTO;
import com.abn.recipe.api.model.recipe.CreatedResponseDTO;
import com.abn.recipe.common.transformer.IngredientConverter;
import com.abn.recipe.domain.service.ingredient.IngredientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/ingredients")
@AllArgsConstructor
@Slf4j
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "create an ingredient.", nickname = "Create an ingredient", notes = "User creates a new ingredient")
    public CreatedResponseDTO create(@Valid @RequestBody IngredientDTO ingredientDTO) {
        log.info("User creates a new ingredient");
        return new CreatedResponseDTO(
                ingredientService.save(IngredientConverter.INSTANCE.dtoToIngredient(ingredientDTO)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "ingredient.", nickname = "Delete an ingredient", notes = "User delete a new ingredient")
    public void delete(
            @ApiParam(value = "delete ingredient ID", required = true) @PathVariable(name = "id") Integer id) {
        log.info("User delete a new ingredient");
        ingredientService.delete(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get a set of ingredient.", nickname = "Get all ingredients", notes = "User get all ingredients")
    public IngredientResponseDTO getAll() {
        log.info("User get all ingredients");
        return new IngredientResponseDTO( IngredientConverter.INSTANCE.ingredientsToDTOs(ingredientService.getAll()));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get an ingredient.", nickname = "Get an ingredient", notes = "User get an ingredient")
    public IngredientDTO getIngredient(@PathVariable @Required int id) {
        log.info("User get an ingredient");
        return IngredientConverter.INSTANCE.ingredientToDTO(ingredientService.getById(id));
    }
}
