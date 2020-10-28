package com.mic3.personservice.service;

import com.mic3.personservice.domain.Person;

import java.util.List;

public interface IPersonService {
    List<Person> getPersons();
}
