package com.iteratia.titanicquest.exception.illegal;

public class PClassIllegalStateException extends PassengerRecordIllegalStateException{
    public PClassIllegalStateException(String pClassRecord) {
        super("Passenger PClass record must be {1, 2, 3}, actual is " + pClassRecord);
    }
}
