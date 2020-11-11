package com.mic3.personservice.service;

import com.mic3.personservice.domain.Person;
import com.mic3.personservice.dto.PersonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service for person entity
 */
public interface IPersonService {
    Page<PersonDTO> getPersons(Pageable pageable);
    PersonDTO createPerson(PersonDTO person);
    PersonDTO loadPerson(long personId);
    PersonDTO updatePerson(long personId, PersonDTO person);
    void deletePerson(long personId);
}
