package com.iteratia.titanicquest.service.impl;

import com.iteratia.titanicquest.model.Passenger;
import com.iteratia.titanicquest.repository.PassengerRepository;
import com.iteratia.titanicquest.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerServiceDB implements PassengerService {

    private final PassengerRepository passengerRepository;

    /**
     * Check if it is first run by select count(*) from passengers
     * if count != 0 - it is NOT first run otherwise it is first run
     * */
    @Override
    public boolean isFirstRun() {
        return this.passengerRepository.isFirstRun() == 0;
    }

    /**
     * Save Passengers form List
     * @param passenger Passengers list for save
     * */
    @Override
    public void addAllPassenger(List<Passenger> passenger) {
        this.passengerRepository.saveAll(passenger);
    }

    /**
     * Get Passengers for current Page
     * */
    @Override
    public List<Passenger> getPassengersPaged(Pageable pageable) {
        return this.passengerRepository.findPassengersPaged(pageable);
    }
}
