package com.iteratia.titanicquest.repository;

import com.iteratia.titanicquest.dto.filter.Filters;
import com.iteratia.titanicquest.dto.search.SearchGuessItem;
import com.iteratia.titanicquest.dto.stats.Statistics;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchRepository {

    List<SearchGuessItem> getSearchGuess(String url, String searchInput);

    Long getSearchedPagesCount(String searchRequest, String url);

    <T> List<T> search(String searchRequest, String url, Class<T> entity, Pageable pageable, Filters filters);

    Number getStatistics(String searchRequest, String url, Filters filters, Statistics statistics);
}
