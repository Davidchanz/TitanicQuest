package com.iteratia.titanicquest.exception.illegal;

public class SiblingsSpousesIllegalStateException extends PassengerRecordIllegalStateException{
    public SiblingsSpousesIllegalStateException(String siblingsSpousesRecord) {
        super("Passenger Siblings/Spouses record must be Number, actual is " + siblingsSpousesRecord);
    }
}
