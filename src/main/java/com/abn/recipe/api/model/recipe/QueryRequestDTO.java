package com.abn.recipe.api.model.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QueryRequestDTO {
    private  List<String> include;
    private  List<String> exclude;
    private  String noServing;
    private  String instruction;
    private  boolean isVegetarian;

}
