package com.iteratia.titanicquest.service;

import com.iteratia.titanicquest.model.Passenger;

import java.util.List;

public interface PassengerService {

    boolean isFirstRun();

    void addAllPassenger(List<Passenger> passenger);
}
