package com.iteratia.titanicquest.service.impl;

import com.iteratia.titanicquest.dto.search.SearchGuessItem;
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
    public <T> List<T> getSearch(String searchRequest, String url, Class<T> entity, Pageable pageable) {
        return this.searchRepository.search(searchRequest, url, entity, pageable);
    }
}
