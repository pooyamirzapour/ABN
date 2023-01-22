package com.abn.recipe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchQueryServiceResponse {
    private Integer no;
    private String name;
    private String instruction;
    private String ingredient;
    private String type;


}
