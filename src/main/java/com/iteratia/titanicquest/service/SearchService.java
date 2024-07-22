package com.iteratia.titanicquest.service;

import com.iteratia.titanicquest.dto.filter.Filters;
import com.iteratia.titanicquest.dto.search.SearchGuessItem;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchService {

    List<SearchGuessItem> getSearchGuess(String url, String searchResult);

    Long getSearchedPagesCount(String searchRequest, String url);

    <T> List<T> getSearch(String searchRequest, String url, Class<T> entityClass, Pageable pageable, Filters filters);
}
