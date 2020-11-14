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

import static com.mic3.personservice.util.TestPersonUtil.getDefaultPerson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    public void shouldCreatePerson(){
        Person person = getDefaultPerson();

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

        when(personRepository.save(getDefaultPerson())).thenReturn(getDefaultPerson());

        PersonDTO personDTO = modelMapper.map(person, PersonDTO.class);
        PersonDTO savedPerson = personService.createPerson(personDTO);
        assertEquals(personDTO.getId(), savedPerson.getId());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    public void shouldReturnPersonList(){
        Pageable p = PageRequest.of(0, 10);
        List<Person> personList = new ArrayList<>();
        personList.add(getDefaultPerson());
        Page<Person> result = new PageImpl<>(personList, p, 1);
        when(personRepository.findAll(p)).thenReturn(result);

        Person person = getDefaultPerson();
        Page<PersonDTO> pagedResult = this.personService.getPersons(PageRequest.of(0, 10));
        assertEquals(1, pagedResult.getTotalElements());
        assertEquals(person.getName(), pagedResult.get().findFirst().get().getName());
    }

    @Test
    public void shouldUpdatePerson(){
        Person person = getDefaultPerson();
        person.setId(1L);
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

        when(personRepository.save(any(Person.class))).thenReturn(person);

        PersonDTO personDTO = modelMapper.map(person, PersonDTO.class);
        PersonDTO savedPerson = personService.updatePerson(person.getId(), personDTO);
        assertEquals(personDTO.getId(), savedPerson.getId());
        verify(personRepository, times(1)).save(any(Person.class));
        verify(personRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void shouldFetchOnePersonById(){
        Person person = getDefaultPerson();
        person.setId(1L);
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

        PersonDTO personDTO = personService.loadPerson(person.getId());
        assertNotNull(personDTO);
        verify(personRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void shouldThrowNotFoundException(){
        Assertions.assertThrows(PersonNotFoundException.class, ()->personService.loadPerson(1));
    }

    @Test
    public void shouldDeletePerson(){
        Person person = getDefaultPerson();
        person.setId(1L);
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        doNothing().when(personRepository).delete(person);
        personService.deletePerson(person.getId());
        verify(personRepository, times(1)).delete(any());
        verify(personRepository, times(1)).findById(any());
    }

    @Test
    public void shouldThrowNotFoundExceptionOnDelete(){
        Assertions.assertThrows(PersonNotFoundException.class, ()->{
            Person person = getDefaultPerson();
            person.setId(1L);
            doNothing().when(personRepository).delete(person);
            personService.deletePerson(person.getId());
        });
        verify(personRepository, never()).delete(any(Person.class));
        verify(personRepository, times(1)).findById(any(Long.class));
    }
}
