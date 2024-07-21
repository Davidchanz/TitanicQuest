package com.iteratia.titanicquest.config;

import com.iteratia.titanicquest.loader.PassengersLoader;
import com.iteratia.titanicquest.model.Passenger;
import com.iteratia.titanicquest.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class LoadDataConfig {

    private final PassengersLoader passengersLoader;

    private final PassengerService passengerService;

    /**
     * When Context Refreshed check is it first run, if true, try to load Passengers
     * if error was occurred ignore it
     * */
    @EventListener
    public void handleContextRefreshEvent(ContextRefreshedEvent ctxRefreshedEvent) {
        if(this.passengerService.isFirstRun()) {
            List<Passenger> passengers = null;
            try {
                passengers = this.passengersLoader.loadPassengers(null); //try to load passengers
            }catch (Exception ignore){
                //if exception was occurred while loading passengers ignore it
            }
            if(passengers != null) //if passengers was loader save them
                this.passengerService.addAllPassenger(passengers);
        }
    }
}
