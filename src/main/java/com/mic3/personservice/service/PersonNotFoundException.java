package com.mic3.personservice.service;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(long id){
        super("Could not find person  with id: " + id);
    }
}
