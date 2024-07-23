package com.iteratia.titanicquest.exception.io;

public class ReadSourceException extends RuntimeException{

    public ReadSourceException(String source, Exception ex){
        super("Cannot load from source '" + source + "'", ex);
    }
}
