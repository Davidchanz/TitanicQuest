package com.iteratia.titanicquest.controller;

import com.iteratia.titanicquest.dto.passenger.PassengerDto;
import com.iteratia.titanicquest.dto.passenger.PassengersDto;
import com.iteratia.titanicquest.mapper.PassengerMapper;
import com.iteratia.titanicquest.model.Passenger;
import com.iteratia.titanicquest.service.PaginationService;
import com.iteratia.titanicquest.service.PassengerService;
import com.iteratia.titanicquest.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("")
    public PassengersDto getPassengers(@RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer pageSize,
                                       @RequestParam String sort,
                                       @RequestParam String order,
                                       @RequestParam String searchRequest){

        List<Passenger> passengers = null;
        if(searchRequest.isEmpty())
            passengers = this.passengerService.getPassengersPaged(this.paginationService.getPage(page, sort, order, pageSize));
        else
            passengers = this.searchService.getSearch(searchRequest,
                    "passengers",
                    Passenger.class,
                    this.paginationService.getPage(page, sort, order, pageSize));

        List<PassengerDto> dtos = passengers.stream()
                .map(this.passengerMapper::modelToDto)
                .collect(Collectors.toList());

        return new PassengersDto(dtos);
    }
}
