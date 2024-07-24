package com.iteratia.titanicquest.repository;

import com.iteratia.titanicquest.model.Passenger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    @Query("SELECT COUNT(*) FROM Passenger p")
    int isFirstRun();

    @Query("SELECT p FROM Passenger p")
    List<Passenger> findPassengersPaged(Pageable pageable);
}
