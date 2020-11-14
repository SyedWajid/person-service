package com.mic3.personservice.service;

import com.mic3.personservice.domain.Person;
import com.mic3.personservice.dto.PersonDTO;
import com.mic3.personservice.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class PersonServiceTest {
    @MockBean
    PersonRepository personRepository;

    @Autowired
    IPersonService personService;

    @Autowired
    ModelMapper modelMapper;

    @Test
    public void personCreateTest(){
        Person person = getDefaultPerson();

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

        when(personRepository.save(getDefaultPerson())).thenReturn(getDefaultPerson());

        PersonDTO personDTO = modelMapper.map(person, PersonDTO.class);
        PersonDTO savedPerson = personService.createPerson(personDTO);
        assertThat(savedPerson.getId()).isEqualTo(person.getId());
    }

    @Test
    public void personsListTest(){
        Pageable p = PageRequest.of(0, 10);
        List<Person> personList = new ArrayList<>();
        personList.add(getDefaultPerson());
        Page<Person> result = new PageImpl<>(personList, p, 1);
        when(personRepository.findAll(p)).thenReturn(result);

        Person person = getDefaultPerson();
        Page<PersonDTO> pagedResult = this.personService.getPersons(PageRequest.of(0, 10));
        assertThat(pagedResult.getTotalElements()).isEqualTo(1);
        assertThat(pagedResult.get().findFirst().get().getName()).isEqualTo(person.getName());
    }

    @Test
    public void deletePersonTest(){
        Person person = getDefaultPerson();
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        doNothing().when(personRepository).delete(person);
        personService.deletePerson(person.getId());
    }

    @Test
    public void personNotFoundTest(){
        Assertions.assertThrows(PersonNotFoundException.class, ()->personService.loadPerson(1));
    }

    private Person getDefaultPerson(){
        Person person = new Person();
        person.setId(1L);
        person.setName("Syed Wajid");
        person.setAge(32);
        return person;
    }
}
