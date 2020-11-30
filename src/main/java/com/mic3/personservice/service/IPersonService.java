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
    /**
     * Return page of persons
     * @param pageable
     * @return Page<PersonDTO>
     */
    Page<PersonDTO> list(Pageable pageable);
    PersonDTO create(PersonDTO person);
    PersonDTO findById(long personId);
    PersonDTO update(long personId, PersonDTO person);
    void delete(long personId);
}
