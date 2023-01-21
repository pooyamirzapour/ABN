package com.abn.recipe.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class Recipe {
    private int id;
    private String name;
    private Set<Ingredient> ingredients;
    private String instruction;
    private String type;
    private LocalDateTime insertDate;
    private LocalDateTime updatedDate;
    private int numberOfServings;
}
