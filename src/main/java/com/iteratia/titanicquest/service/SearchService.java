package com.iteratia.titanicquest.service;

import com.iteratia.titanicquest.dto.filter.Filters;
import com.iteratia.titanicquest.dto.search.SearchGuessItem;
import com.iteratia.titanicquest.dto.stats.Statistics;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchService {

    List<SearchGuessItem> getSearchGuess(String url, String searchResult);

    int getSearchedPagesCount(String searchRequest, String url, Filters filters);

    <T> List<T> getSearch(String searchRequest, String url, Class<T> entityClass, Pageable pageable, Filters filters);

    Number getStatistics(String searchRequest, String url, Filters filters, Statistics statistics);
}
