package com.abn.recipe.api.controller;

import com.abn.recipe.api.model.recipe.CreatedResponseDTO;
import com.abn.recipe.api.model.recipe.RecipeRequestDTO;
import com.abn.recipe.api.model.recipe.RecipeResponseDTO;
import com.abn.recipe.common.transformer.RecipeConvertor;
import com.abn.recipe.domain.service.recipe.RecipeService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipe")
@AllArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    private final HttpServletRequest httpServletRequest;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "recipe.", nickname = "Create Recipe", notes = "User creates a new recipe with ingredients")
    public CreatedResponseDTO create(@RequestBody RecipeRequestDTO recipeRequestDTO) {
        return new CreatedResponseDTO(recipeService.save(RecipeConvertor.INSTANCE.dtoToRecipe(recipeRequestDTO)));
    }

    @PatchMapping()
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "recipe.", nickname = "Update Recipe", notes = "User updates an existing recipe with ingredients")
    public CreatedResponseDTO update(@RequestBody RecipeRequestDTO recipeRequestDTO) {
        return new CreatedResponseDTO(recipeService.update(RecipeConvertor.INSTANCE.dtoToRecipe(recipeRequestDTO)));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "recipe.", nickname = "Get Recipe", notes = "User get a recipe by id")
    public RecipeResponseDTO get(@PathVariable int id) {
        return RecipeConvertor.INSTANCE.recipeToDTO(recipeService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "recipe.", nickname = "Remove Recipe", notes = "User remove a recipe by id")
    public void delete(@Valid @PathVariable int id) {
        recipeService.delete(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "recipe.", nickname = "Get Recipe by query", notes = "User get a recipe by query")
    public List<RecipeResponseDTO> searchRecipe(@RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        return recipeService.search(parameterMap, page, size);
    }

}
