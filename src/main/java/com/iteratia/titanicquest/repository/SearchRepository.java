package com.iteratia.titanicquest.repository;

import com.iteratia.titanicquest.dto.search.SearchGuessItem;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchRepository {

    List<SearchGuessItem> getSearchGuess(String url, String searchInput);

    Long getSearchedPagesCount(String searchRequest, String url);

    <T> List<T> search(String searchRequest, String url, Class<T> entity, Pageable pageable);
}
