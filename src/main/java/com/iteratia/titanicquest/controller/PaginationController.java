package com.iteratia.titanicquest.controller;

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

    @GetMapping("/{url}")
    public PaginationDto getPagination(@RequestParam(defaultValue = "") String searchRequest,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "50") Integer pageSize,
                                       @PathVariable String url){

        PaginationDto paginationItemDto = new PaginationDto();
        Long searchedPagesCount = this.searchService.getSearchedPagesCount(searchRequest, url); //get items count
        List<Boolean> pagination = this.paginationService.getPagination(searchedPagesCount, page, pageSize); // get pagination
        paginationItemDto.setPagination(pagination);
        return paginationItemDto;
    }
}
