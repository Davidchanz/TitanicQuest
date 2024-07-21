package com.iteratia.titanicquest.controller;

import com.iteratia.titanicquest.dto.search.SearchGuess;
import com.iteratia.titanicquest.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/{entity}/guess/{searchInput}")
    public SearchGuess getSearchGuess(@PathVariable("entity") String entity,
                                      @PathVariable("searchInput") String searchInput){
        SearchGuess searchResult = new SearchGuess();
        if(searchInput.isEmpty()){
            searchResult.setResults(new ArrayList<>()); //if no search set guess empty
        }else {
            searchResult.setResults(this.searchService.getSearchGuess(entity, searchInput)); // get search guess
        }

        return searchResult;
    }
}
