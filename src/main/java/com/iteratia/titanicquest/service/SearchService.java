package com.iteratia.titanicquest.service;

import com.iteratia.titanicquest.dto.search.SearchGuessItem;

import java.util.List;

public interface SearchService {

    List<SearchGuessItem> getSearchGuess(String url, String searchResult);

    int getSearchedPagesCount(String searchRequest, String url);
}
