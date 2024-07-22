package com.iteratia.titanicquest.controller;

import com.iteratia.titanicquest.dto.filter.Filters;
import com.iteratia.titanicquest.dto.stats.Statistics;
import com.iteratia.titanicquest.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final SearchService searchService;

    /**
     * Method Use POST instead of GET for compute and process query for get Searched Filtered Ordered and Paged request
     * this params provides in body parts
     * */
    @PostMapping("/{url}")
    public Number getStatistics(@RequestPart(required = false) Filters filters,
                                @RequestPart(required = false) Statistics statistics,
                                @RequestParam String searchRequest,
                                @PathVariable String url){

        return this.searchService.getStatistics(searchRequest, url, filters, statistics);
    }
}
