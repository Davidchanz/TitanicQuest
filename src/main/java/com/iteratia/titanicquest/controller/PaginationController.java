package com.iteratia.titanicquest.controller;

import com.iteratia.titanicquest.dto.filter.Filters;
import com.iteratia.titanicquest.dto.pagination.Pagination;
import com.iteratia.titanicquest.dto.pagination.PaginationDto;
import com.iteratia.titanicquest.service.PaginationService;
import com.iteratia.titanicquest.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagination")
@RequiredArgsConstructor
public class PaginationController {

    private final PaginationService paginationService;

    private final SearchService searchService;

    /**
     * Method Use POST instead of GET because it needs RequestBody to compute request
     * */
    @PostMapping("/{url}")
    public PaginationDto getPagination(@RequestParam(defaultValue = "") String searchRequest,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "50") Integer pageSize,
                                       @RequestBody(required = false) Filters filters,
                                       @PathVariable String url){
        if(filters == null)
            filters = new Filters();

        PaginationDto paginationItemDto = new PaginationDto();
        int searchedPagesCount = this.searchService.getSearchedPagesCount(searchRequest, url, filters); //get items count
        List<Pagination> pagination = this.paginationService.getPagination(searchedPagesCount, page, pageSize); // get pagination
        paginationItemDto.setPagination(pagination);
        return paginationItemDto;
    }
}
