package com.mic3.personservice.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mic3.personservice.dto.PersonDTO;
import com.mic3.personservice.service.IPersonService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PersonControllerTest extends AbstractRestTest {

    public static final String URL = "/api/v1/persons";

    @MockBean
    IPersonService personService;

    @Test
    public void personsTest() throws Exception {

        Pageable pageable = PageRequest.of(0, 20);
        List<PersonDTO> personList = new ArrayList<>();
        PersonDTO personDTO = getDefaultPerson();
        personList.add(personDTO);
        Page<PersonDTO> result = new RestResponsePage<>(personList);
        Mockito.when(personService.getPersons(pageable)).thenReturn(result);

        MvcResult response = mvc.perform(get(URL)).andDo(print()).
                andExpect(status().isOk()).andReturn();
        Page<PersonDTO> pagedResult = mapFromJson(response.getResponse().getContentAsString(), new TypeReference<RestResponsePage<PersonDTO>>() {});
        assertNotNull(pagedResult);
        assertEquals(1, pagedResult.getTotalElements());
        assertEquals(personDTO.getName(), pagedResult.get().findFirst().get().getName());
    }

    @Test
    public void createPersonTest() throws Exception {

        PersonDTO personDTO = getDefaultPerson();
        Mockito.when(personService.createPerson(personDTO)).thenReturn(personDTO);

        String inputJson = mapToJson(personDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        PersonDTO personCreated = mapFromJson(content, PersonDTO.class);
        assertNotNull(personCreated);
        assertEquals(personCreated.getId(), 1);
        assertEquals(personDTO.getName(), personCreated.getName());
    }

    private PersonDTO getDefaultPerson(){
        PersonDTO person = new PersonDTO();
        person.setId(1L);
        person.setName("Syed Wajid");
        person.setAge(32);
        return person;
    }
}
