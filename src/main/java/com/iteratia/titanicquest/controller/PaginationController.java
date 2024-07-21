package com.iteratia.titanicquest.controller;

import com.iteratia.titanicquest.dto.pagination.PaginationDto;
import com.iteratia.titanicquest.service.PaginationService;
import com.iteratia.titanicquest.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagination")
@RequiredArgsConstructor
public class PaginationController {

    private final PaginationService paginationService;

    private final PassengerService passengerService;

    @GetMapping("/{url}")
    public PaginationDto getPagination(@RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer pageSize,
                                       @PathVariable String url){

        PaginationDto paginationItemDto = new PaginationDto();
        int searchedPagesCount = this.passengerService.getAllPassengers().size(); //get passengers count
        List<Boolean> pagination = this.paginationService.getPagination(searchedPagesCount, page, pageSize); // get pagination
        paginationItemDto.setPagination(pagination);
        return paginationItemDto;
    }
}
