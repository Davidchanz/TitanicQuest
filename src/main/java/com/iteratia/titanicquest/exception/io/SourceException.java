package com.iteratia.titanicquest.exception.io;

public class SourceException extends RuntimeException{

    public SourceException(String source, Exception ex){
        super("Cannot load from source '" + source + "'", ex);
    }
}
