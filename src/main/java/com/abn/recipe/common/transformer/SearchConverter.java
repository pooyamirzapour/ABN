package com.abn.recipe.common.transformer;

import com.abn.recipe.domain.model.SearchQueryServiceResponse;
import com.abn.recipe.repository.recipe.SearchQueryEntityModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * A transformer for queries
 */
@Mapper
public interface SearchConverter {
    SearchConverter INSTANCE = Mappers.getMapper(SearchConverter.class);

    List<SearchQueryServiceResponse> queryEntityToQueryService( List<SearchQueryEntityModel> searchQueryEntityModel);

}
