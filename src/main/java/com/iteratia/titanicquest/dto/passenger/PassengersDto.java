package com.iteratia.titanicquest.dto.passenger;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PassengersDto {
    private List<PassengerDto> passengers;
}
