package com.iteratia.titanicquest.controller;

import com.iteratia.titanicquest.dto.filter.Filters;
import com.iteratia.titanicquest.dto.passenger.PassengerDto;
import com.iteratia.titanicquest.dto.passenger.PassengersDto;
import com.iteratia.titanicquest.mapper.PassengerMapper;
import com.iteratia.titanicquest.model.Passenger;
import com.iteratia.titanicquest.service.PaginationService;
import com.iteratia.titanicquest.service.PassengerService;
import com.iteratia.titanicquest.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengersController {

    private final PassengerService passengerService;

    private final PaginationService paginationService;

    private final SearchService searchService;

    private final PassengerMapper passengerMapper;

    /**
     * Method Use POST instead of GET for compute and process query for get Searched Filtered Ordered and Paged request
     * this params provides in body parts
     * */
    @PostMapping("")
    public PassengersDto getPassengers(@RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer pageSize,
                                       @RequestParam String sort,
                                       @RequestParam String order,
                                       @RequestParam String searchRequest,
                                       @RequestBody(required = false) Filters filters){

        List<Passenger> passengers = null;
        if(searchRequest.isEmpty() && (filters == null || filters.getFilters().isEmpty()))
            passengers = this.passengerService.getPassengersPaged(this.paginationService.getPage(page, sort, order, pageSize));
        else
            passengers = this.searchService.getSearch(searchRequest,
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
