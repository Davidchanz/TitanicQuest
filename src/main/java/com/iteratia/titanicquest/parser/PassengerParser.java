package com.iteratia.titanicquest.parser;

import com.iteratia.titanicquest.exception.illegal.*;
import com.iteratia.titanicquest.model.PClass;
import com.iteratia.titanicquest.model.Passenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class PassengerParser {
    static Logger logger = LoggerFactory.getLogger(PassengerParser.class);

    /**
     * Parse Passengers from record list
     * @param record list of string records, if size != 8 throw <RecordSizeIllegalStateException>
     *
     * */
    public static Passenger parsePassenger(List<String> record){
        if(record.size() != 8)
            throw new RecordSizeIllegalStateException(record.size());

        logger.info("Parse Passenger from Record '{}'",
                List.of(Map.of("record", record)));

        Passenger passenger = new Passenger();

        passenger.setSurvived(record.get(0).equals("1"));
        passenger.setPClass(PClass.values()[parsePClass(record.get(1))-1]); // get PClass value by index
        passenger.setName(record.get(2));
        passenger.setSex(record.get(3));
        passenger.setAge(parseAge(record.get(4)));
        passenger.setSiblingsSpouses(parseSiblingsSpouses(record.get(5)));
        passenger.setParentsChildren(parseParentsChildren(record.get(6)));
        passenger.setFare(new BigDecimal(record.get(7)));

        return passenger;
    }

    /**
     * Parse ParentsChildren from String record to int
     * @param parentsChildrenRecord record to parse
     * if parse failed throw <ParentsChildrenRecordIllegalStateException>
     * */
    private static int parseParentsChildren(String parentsChildrenRecord) {
        int siblingsSpouses;
        try {
            siblingsSpouses = Integer.parseInt(parentsChildrenRecord);
        } catch (NumberFormatException ex) {
            throw new ParentsChildrenRecordIllegalStateException(parentsChildrenRecord);
        }
        return siblingsSpouses;
    }

    /**
     * Parse SiblingsSpouses from String record to int
     * @param siblingsSpousesRecord record to parse
     * if parse failed throw <SiblingsSpousesIllegalStateException>
     * */
    private static int parseSiblingsSpouses(String siblingsSpousesRecord) {
        int siblingsSpouses;
        try {
            siblingsSpouses = Integer.parseInt(siblingsSpousesRecord);
        } catch (NumberFormatException ex) {
            throw new SiblingsSpousesIllegalStateException(siblingsSpousesRecord);
        }
        return siblingsSpouses;
    }

    /**
     * Parse Age from String record to int
     * @param ageRecord record to parse
     * if parse failed throw <AgeIllegalStateException>
     * */
    private static float parseAge(String ageRecord) {
        float age;
        try {
            age = Float.parseFloat(ageRecord);
        } catch (NumberFormatException ex) {
            throw new AgeIllegalStateException(ageRecord);
        }
        return age;
    }

    /**
     * Parse PClass from String record to int
     * @param pClassRecord record to parse
     * if parse failed throw <PClassIllegalStateException>
     * */
    private static int parsePClass(String pClassRecord){
        int pClass;
        try {
            pClass = Integer.parseInt(pClassRecord);
        } catch (NumberFormatException ex) {
            throw new PClassIllegalStateException(pClassRecord);
        }
        return pClass;
    }
}
