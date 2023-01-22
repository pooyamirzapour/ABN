package com.abn.recipe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SearchQueryServiceRequest {
    private int page;
    private int size;
    private List<String> includes;
    private List<String> excludes;
    private Integer noServings;
    private String instruction;
    private String type;

}
