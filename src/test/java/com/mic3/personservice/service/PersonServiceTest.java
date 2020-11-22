package com.mic3.personservice.service;

import com.mic3.personservice.domain.Person;
import com.mic3.personservice.dto.PersonDTO;
import com.mic3.personservice.repository.PersonRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mic3.personservice.util.TestPersonUtil.getDefaultPerson;
import static com.mic3.personservice.util.TestPersonUtil.getDefaultPersonDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
@ExtendWith({MockitoExtension.class})
public class PersonServiceTest {
    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonServiceImpl personService;

    @Mock
    ModelMapper modelMapper;

    @Test
    public void shouldCreatePerson(){
        Person person = getDefaultPerson();
        PersonDTO personDTO = getDefaultPersonDTO();
        when(modelMapper.map(person, PersonDTO.class)).thenReturn(personDTO);
        when(modelMapper.map(personDTO, Person.class)).thenReturn(person);
        when(personRepository.save(getDefaultPerson())).thenReturn(getDefaultPerson());

        PersonDTO savedPerson = personService.create(personDTO);
        assertEquals(personDTO.getId(), savedPerson.getId());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    public void shouldReturnPersonList(){
        Person person = getDefaultPerson();
        Pageable p = PageRequest.of(0, 10);
        List<Person> personList = new ArrayList<>();
        personList.add(getDefaultPerson());
        Page<Person> result = new PageImpl<>(personList, p, 1);
        when(personRepository.findAll(p)).thenReturn(result);
        when(modelMapper.map(person, PersonDTO.class)).thenReturn(getDefaultPersonDTO());

        Page<PersonDTO> pagedResult = this.personService.getPersons(PageRequest.of(0, 10));
        assertEquals(1, pagedResult.getTotalElements());
        assertEquals(person.getName(), pagedResult.get().findFirst().get().getName());
    }

    @Test
    public void shouldUpdatePerson(){
        Person person = getDefaultPerson();
        PersonDTO personDTO = getDefaultPersonDTO();
        person.setId(1L);
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

        when(personRepository.save(any(Person.class))).thenReturn(person);

        when(modelMapper.map(person, PersonDTO.class)).thenReturn(personDTO);
        when(modelMapper.map(personDTO, Person.class)).thenReturn(person);

        PersonDTO savedPerson = personService.update(person.getId(), personDTO);
        assertEquals(personDTO.getId(), savedPerson.getId());
        verify(personRepository, times(1)).save(any(Person.class));
        verify(personRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void shouldFetchOnePersonById(){
        Person person = getDefaultPerson();
        person.setId(1L);
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

        when(modelMapper.map(person, PersonDTO.class)).thenReturn(getDefaultPersonDTO());

        PersonDTO personDTO = personService.findById(person.getId());
        assertNotNull(personDTO);
        verify(personRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void shouldThrowNotFoundException(){
        Assertions.assertThrows(PersonNotFoundException.class, ()->personService.findById(1));
    }

    @Test
    public void shouldDeletePerson(){
        Person person = getDefaultPerson();
        person.setId(1L);
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        doNothing().when(personRepository).delete(person);
        personService.delete(person.getId());
        verify(personRepository, times(1)).delete(any());
        verify(personRepository, times(1)).findById(any());
    }

    @Test
    public void shouldThrowNotFoundExceptionOnDelete(){
        Assertions.assertThrows(PersonNotFoundException.class, ()->{
            Person person = getDefaultPerson();
            person.setId(1L);
            personService.delete(person.getId());
        });
        verify(personRepository, never()).delete(any(Person.class));
        verify(personRepository, times(1)).findById(any(Long.class));
    }
}
