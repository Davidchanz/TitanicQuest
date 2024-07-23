package com.iteratia.titanicquest.exception.notFound;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String url){
        super("Request not supported for entity with url '"+url+"'");
    }
}