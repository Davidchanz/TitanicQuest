package com.iteratia.titanicquest.loader;

import com.iteratia.titanicquest.model.Passenger;

import java.util.List;

public interface PassengersLoader {

    List<Passenger> loadPassengers(String source);
}
