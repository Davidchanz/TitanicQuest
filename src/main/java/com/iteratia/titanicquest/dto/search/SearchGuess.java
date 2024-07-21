package com.iteratia.titanicquest.dto.search;

import lombok.Data;

import java.util.List;

@Data
public class SearchGuess {
    private List<SearchGuessItem> results;
}
