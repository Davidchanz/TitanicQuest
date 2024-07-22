package com.iteratia.titanicquest.service.impl;

import com.iteratia.titanicquest.dto.filter.Filters;
import com.iteratia.titanicquest.dto.search.SearchGuessItem;
import com.iteratia.titanicquest.dto.stats.Statistics;
import com.iteratia.titanicquest.repository.SearchRepository;
import com.iteratia.titanicquest.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceDB implements SearchService {

    private final SearchRepository searchRepository;

    @Override
    public List<SearchGuessItem> getSearchGuess(String url, String searchResult) {
        return this.searchRepository.getSearchGuess(url, searchResult); // get search guess
    }

    @Override
    public Long getSearchedPagesCount(String searchRequest, String url) {
        return this.searchRepository.getSearchedPagesCount(searchRequest, url);
    }

    @Override
    public <T> List<T> getSearch(String searchRequest, String url, Class<T> entity, Pageable pageable, Filters filters) {
        return this.searchRepository.search(searchRequest, url, entity, pageable, filters);
    }

    @Override
    public Number getStatistics(String searchRequest, String url, Filters filters, Statistics statistics) {
        return this.searchRepository.getStatistics(searchRequest, url, filters, statistics);
    }
}
