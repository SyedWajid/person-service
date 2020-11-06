package com.mic3.personservice.service;

import com.mic3.personservice.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service for person entity
 */
public interface IPersonService {
    Page<Person> getPersons(Pageable pageable);
    Person createPerson(Person person);
    Optional<Person> loadPerson(long personId);
    Optional<Person> updatePerson(long personId, Person person);
    Optional<Person> deletePerson(long personId);
}
