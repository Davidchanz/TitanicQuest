package com.iteratia.titanicquest.controller;

import com.iteratia.titanicquest.dto.filter.Filters;
import com.iteratia.titanicquest.dto.stats.Statistics;
import com.iteratia.titanicquest.exception.illegal.StatisticsRequestIllegalStateException;
import com.iteratia.titanicquest.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final SearchService searchService;

    /**
     * Method Use POST instead of GET because it needs RequestBody to compute request
     * */
    @PostMapping("/{url}")
    public Number getStatistics(@RequestPart(required = false) Filters filters,
                                @RequestPart(required = false) Statistics statistics,
                                @RequestParam(defaultValue = "") String searchRequest,
                                @PathVariable String url){
        if(filters == null)
            filters = new Filters();

        if(statistics == null)
            throw new StatisticsRequestIllegalStateException();

        return this.searchService.getStatistics(searchRequest, url, filters, statistics);
    }
}
