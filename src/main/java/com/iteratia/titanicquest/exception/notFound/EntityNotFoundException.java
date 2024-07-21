package com.iteratia.titanicquest.exception.notFound;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String url){
        super("Search not supported for entity with url '"+url+"'");
    }
}