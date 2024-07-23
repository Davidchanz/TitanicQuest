package com.iteratia.titanicquest.controller;

import com.iteratia.titanicquest.dto.filter.Filters;
import com.iteratia.titanicquest.dto.passenger.PassengerDto;
import com.iteratia.titanicquest.dto.passenger.PassengersDto;
import com.iteratia.titanicquest.mapper.PassengerMapper;
import com.iteratia.titanicquest.model.Passenger;
import com.iteratia.titanicquest.service.PaginationService;
import com.iteratia.titanicquest.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengersController {

    private final PaginationService paginationService;

    private final SearchService searchService;

    private final PassengerMapper passengerMapper;

    /**
     * Method Use POST instead of GET because it needs RequestBody to compute request
     * */
    @PostMapping("")
    public PassengersDto getPassengers(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "50") Integer pageSize,
                                       @RequestParam(defaultValue = "id") String sort,
                                       @RequestParam(defaultValue = "ASC") String order,
                                       @RequestParam(defaultValue = "") String searchRequest,
                                       @RequestBody(required = false) Filters filters){
        if(filters == null)
            filters = new Filters();

        List<Passenger> passengers = this.searchService.getSearch(searchRequest,
                    "passengers",
                    Passenger.class,
                    this.paginationService.getPage(page, sort, order, pageSize),
                    filters);

        List<PassengerDto> dtos = passengers.stream()
                .map(this.passengerMapper::modelToDto)
                .collect(Collectors.toList());

        return new PassengersDto(dtos);
    }
}
