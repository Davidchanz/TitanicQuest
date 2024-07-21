package com.iteratia.titanicquest.exception.illegal;

public class AgeIllegalStateException extends PassengerRecordIllegalStateException{
    public AgeIllegalStateException(String ageRecord) {
        super("Passenger Age record must be integer, actual is " + ageRecord);
    }
}
