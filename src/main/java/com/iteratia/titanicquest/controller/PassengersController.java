package com.iteratia.titanicquest.controller;

import com.iteratia.titanicquest.dto.passenger.PassengerDto;
import com.iteratia.titanicquest.dto.passenger.PassengersDto;
import com.iteratia.titanicquest.mapper.PassengerMapper;
import com.iteratia.titanicquest.model.Passenger;
import com.iteratia.titanicquest.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengersController {

    private final PassengerService passengerService;

    private final PassengerMapper passengerMapper;

    @GetMapping("")
    public PassengersDto getPassengers(){
        List<Passenger> passengers = this.passengerService.getAllPassengers();
        List<PassengerDto> dtos = passengers.stream()
                .map(this.passengerMapper::modelToDto)
                .collect(Collectors.toList());
        return new PassengersDto(dtos);
    }
}
