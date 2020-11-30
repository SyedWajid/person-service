package com.mic3.personservice.rest;

import com.mic3.personservice.dto.PersonDTO;
import com.mic3.personservice.service.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.mic3.personservice.util.TestPersonUtil.getDefaultPersonDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class PersonControllerUnitTest {
    @Mock
    PersonServiceImpl personService;

    @InjectMocks
    PersonController personController;

    @Test
    public void shouldCreatePerson(){
        PersonDTO personDTO = getDefaultPersonDTO();
        when(personService.create(getDefaultPersonDTO())).thenReturn(getDefaultPersonDTO());

        ResponseEntity<PersonDTO> responseEntity = personController.createPerson(personDTO);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        PersonDTO savedPerson = responseEntity.getBody();
        assertEquals(personDTO.getId(), savedPerson.getId());
        verify(personService, times(1)).create(any(PersonDTO.class));
    }

    @Test
    public void shouldReturnPersonList(){
        PersonDTO personDTO = getDefaultPersonDTO();
        Pageable p = PageRequest.of(0, 10);
        List<PersonDTO> personList = new ArrayList<>();
        personList.add(personDTO);
        Page<PersonDTO> result = new PageImpl<>(personList, p, 1);
        when(personService.list(p)).thenReturn(result);

        ResponseEntity<Page<PersonDTO>> responseEntity = this.personController.getPersons(PageRequest.of(0, 10));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Page<PersonDTO> pagedResult = responseEntity.getBody();
        assertEquals(1, pagedResult.getTotalElements());
        assertEquals(personDTO.getName(), pagedResult.get().findFirst().get().getName());
        verify(personService, times(1)).list(any(Pageable.class));
    }

    @Test
    public void shouldUpdatePerson(){
        PersonDTO personDTO = getDefaultPersonDTO();

        when(personService.update(anyLong(), any(PersonDTO.class))).thenReturn(personDTO);

        ResponseEntity<PersonDTO> responseEntity = personController.updatePerson(personDTO.getId(), personDTO);
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        PersonDTO savedPerson = responseEntity.getBody();
        assertEquals(personDTO.getId(), savedPerson.getId());
        verify(personService, times(1)).update(any(Long.class), any(PersonDTO.class));
    }

    @Test
    public void shouldFetchOnePersonById(){
        PersonDTO personDTO = getDefaultPersonDTO();
        when(personService.findById(personDTO.getId())).thenReturn(personDTO);

        ResponseEntity<PersonDTO> responseEntity = personController.list(personDTO.getId());
        assertNotNull(personDTO);
        verify(personService, times(1)).findById(any(Long.class));
    }

    @Test
    public void shouldDeletePerson(){
        PersonDTO personDTO = getDefaultPersonDTO();
        doNothing().when(personService).delete(personDTO.getId());
        personController.deletePerson(personDTO.getId());
        verify(personService, times(1)).delete(anyLong());
    }
}
