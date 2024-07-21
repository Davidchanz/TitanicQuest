package com.iteratia.titanicquest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PassengersDto {
    private List<PassengerDto> passengers;
}
