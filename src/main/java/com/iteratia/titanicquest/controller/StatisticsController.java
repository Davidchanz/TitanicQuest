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

    @GetMapping("/{url}")
    public Number getStatistics(@RequestPart(required = false) Filters filters,
                              @RequestPart(required = false) Statistics statistics,
                              @RequestParam String searchRequest,
                              @PathVariable String url){

        return this.searchService.getStatistics(searchRequest, url, filters, statistics);
    }
}
