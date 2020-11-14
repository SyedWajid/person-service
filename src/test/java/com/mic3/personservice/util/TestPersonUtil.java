package com.mic3.personservice.util;

import com.mic3.personservice.domain.Person;
import com.mic3.personservice.dto.PersonDTO;

public class TestPersonUtil {
    public static Person getDefaultPerson(){
        Person personDTO = new Person();
        personDTO.setName("Syed Wajid");
        personDTO.setAge(32);
        return personDTO;
    }

    public static PersonDTO getDefaultPersonDTO(){
        PersonDTO person = new PersonDTO();
        person.setId(1L);
        person.setName("Syed Wajid");
        person.setAge(32);
        return person;
    }
}
