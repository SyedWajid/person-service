package com.mic3.personservice.service;

import com.mic3.personservice.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * Service for person entity
 */
public interface IPersonService {
    Page<Person> getPersons(Pageable pageable);
}
