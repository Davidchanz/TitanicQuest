package com.iteratia.titanicquest.repository;

import com.iteratia.titanicquest.dto.search.SearchGuessItem;

import java.util.List;

public interface SearchRepository {

    List<SearchGuessItem> getSearchGuess(String url, String searchInput);

    int getSearchedPagesCount(String searchRequest, String url);
}
