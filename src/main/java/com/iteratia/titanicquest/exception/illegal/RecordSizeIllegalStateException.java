package com.iteratia.titanicquest.exception.illegal;

public class RecordSizeIllegalStateException extends PassengerRecordIllegalStateException{

    public RecordSizeIllegalStateException(int actualSize){
        super("Passenger record size must be equals 8, actualSize is " + actualSize);
    }
}
