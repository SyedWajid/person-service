package com.mic3.personservice.util;

import com.mic3.personservice.domain.Person;
import com.mic3.personservice.dto.PersonDTO;

/**
 * Test util for person service
 */
public class TestPersonUtil {
    /**
     * Creates and returns default person entity
     * @return Person
     */
    public static Person getDefaultPerson(){
        Person personDTO = new Person();
        personDTO.setName("Syed Wajid");
        personDTO.setSurname("Wajid");
        personDTO.setAge(32);
        return personDTO;
    }

    /**
     * Creates and returns default person dto
     * @return PersonDTO
     */
    public static PersonDTO getDefaultPersonDTO(){
        PersonDTO person = new PersonDTO();
        person.setId(1L);
        person.setName("Syed Wajid");
        person.setSurname("Wajid");
        person.setAge(32);
        return person;
    }
}
