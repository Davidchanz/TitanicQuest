package com.iteratia.titanicquest.exception.illegal;

public class ParentsChildrenRecordIllegalStateException extends PassengerRecordIllegalStateException{
    public ParentsChildrenRecordIllegalStateException(String parentsChildrenRecord) {
        super("Passenger Parents/Children record must be integer, actual is " + parentsChildrenRecord);
    }
}
