package com.iteratia.titanicquest.exception.illegal;

public class StatisticsRequestIllegalStateException extends RuntimeException{
    public StatisticsRequestIllegalStateException(){
        super("Statistics Request is empty");
    }
}
