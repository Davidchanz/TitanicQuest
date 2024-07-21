package com.iteratia.titanicquest.service;

import com.iteratia.titanicquest.model.Passenger;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PassengerService {

    boolean isFirstRun();

    void addAllPassenger(List<Passenger> passenger);

    List<Passenger> getPassengersPaged(Pageable pageable);
}
