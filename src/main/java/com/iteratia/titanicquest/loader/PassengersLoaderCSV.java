package com.iteratia.titanicquest.loader;

import com.iteratia.titanicquest.exception.io.ReadSourceException;
import com.iteratia.titanicquest.model.Passenger;
import com.iteratia.titanicquest.parser.PassengerParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class PassengersLoaderCSV implements PassengersLoader{

    static Logger logger = LoggerFactory.getLogger(PassengersLoaderCSV.class);

    @Value("${iteratia.titanicquest.load-file-url}")
    private String FILE_URL;

    private final String DELIMITER = ",";

    /**
     * Load Passenger from CSV File by SourceUrl
     * @param source url fro csv file, if null use from property ${file.url}
     * */
    @Override
    public List<Passenger> loadPassengers(String source){
        List<List<String>> records = new ArrayList<>();
        if(source == null)
            source = FILE_URL; //if no source was provided use source from property file

        logger.info("Start Passengers Loading from Source '{}'", source);

        try {
            BufferedInputStream in = new BufferedInputStream(new URL(source).openStream()); //read csv file from url
            try (Scanner scanner = new Scanner(in)) {
                scanner.nextLine(); // skip header line
                while (scanner.hasNextLine()) {
                    records.add(getRecordFromLine(scanner.nextLine())); //parse csv file line by line
                }
            }
        } catch (IOException e) {
            logger.error("End Passengers Loading with Error");
            throw new ReadSourceException(source, e);
        }
        // parse csv record to passenger
        List<Passenger> passengers = records.stream()
                .map(PassengerParser::parsePassenger)
                .collect(Collectors.toList());

        logger.info("End Passengers Loading from Source Result '{}'", passengers);

        return passengers;
    }

    /**
     * Parse line from CSV file by COMMA delimiter
     * @param line line for parse
     * */
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

}
