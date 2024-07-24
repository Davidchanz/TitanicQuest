package com.iteratia.titanicquest.config;

import com.iteratia.titanicquest.loader.PassengersLoader;
import com.iteratia.titanicquest.model.Passenger;
import com.iteratia.titanicquest.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class LoadDataConfig {

    static Logger logger = LoggerFactory.getLogger(LoadDataConfig.class);

    private final PassengersLoader passengersLoader;

    private final PassengerService passengerService;

    /**
     * When Context Refreshed check is it first run, if true, try to load Passengers
     * if error was occurred ignore it
     * */
    @EventListener
    public void handleContextRefreshEvent(ContextRefreshedEvent ctxRefreshedEvent) {
        if(this.passengerService.isFirstRun()) {

            logger.info("Start Passengers Loading");

            List<Passenger> passengers = this.passengersLoader.loadPassengers(null); //try to load passengers

            logger.info("End Passengers Loading Successfully");

            this.passengerService.addAllPassenger(passengers);
        }
    }
}
