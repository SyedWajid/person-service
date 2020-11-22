package com.mic3.personservice.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mic3.personservice.dto.PersonDTO;
import com.mic3.personservice.service.IPersonService;
import org.junit.jupiter.api.Test;
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

import static com.mic3.personservice.util.TestPersonUtil.getDefaultPersonDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PersonControllerTest extends AbstractRestTest {

    public static final String URI = "/api/v1/persons";
    public static final String URI_ONE = URI + "/{personId}";

    @MockBean
    IPersonService personService;

    @Test
    public void personsTest() throws Exception {

        Pageable pageable = PageRequest.of(0, 20);
        List<PersonDTO> personList = new ArrayList<>();
        PersonDTO personDTO = getDefaultPersonDTO();
        personList.add(personDTO);
        Page<PersonDTO> result = new RestResponsePage<>(personList);
        when(personService.getPersons(pageable)).thenReturn(result);

        MvcResult response = mvc.perform(get(URI)).andDo(print()).
                andExpect(status().isOk()).andReturn();
        Page<PersonDTO> pagedResult = mapFromJson(response.getResponse().getContentAsString(),
                                                  new TypeReference<RestResponsePage<PersonDTO>>() {});
        assertNotNull(pagedResult);
        assertEquals(1, pagedResult.getTotalElements());
        assertEquals(personDTO.getName(), pagedResult.get().findFirst().get().getName());
    }

    @Test
    public void createPersonTest() throws Exception {

        PersonDTO personDTO = getDefaultPersonDTO();
        when(personService.create(personDTO)).thenReturn(personDTO);

        String inputJson = mapToJson(personDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andDo(print()).
                andExpect(status().isCreated()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        PersonDTO personCreated = mapFromJson(content, PersonDTO.class);

        verify(personService, times(1)).create(any(PersonDTO.class));
        assertNotNull(personCreated);
        assertEquals(personCreated.getId(), 1);
        assertEquals(personDTO.getName(), personCreated.getName());
    }

    @Test
    public void updatePersonTest() throws Exception {

        PersonDTO personDTO = getDefaultPersonDTO();
        when(personService.update(1, personDTO)).thenReturn(personDTO);

        String inputJson = mapToJson(personDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(URI_ONE, personDTO.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andDo(print())
                .andExpect(status().isAccepted()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        PersonDTO personUpdated = mapFromJson(content, PersonDTO.class);

        verify(personService, times(1)).update(any(Long.class), any(PersonDTO.class));
        assertNotNull(personUpdated);
        assertEquals(personUpdated.getId(), 1);
    }

    @Test
    public void loadPersonTest() throws Exception {

        PersonDTO personDTO = getDefaultPersonDTO();
        when(personService.findById(1)).thenReturn(personDTO);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URI_ONE, personDTO.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print()).andExpect(status()
                .isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        PersonDTO personFetched = mapFromJson(content, PersonDTO.class);

        verify(personService, times(1)).findById(any(Long.class));
        assertNotNull(personFetched);
        assertEquals(personFetched.getId(), 1);
    }

    @Test
    public void deletePersonTest() throws Exception {

        PersonDTO personDTO = getDefaultPersonDTO();
        doNothing().when(personService).delete(personDTO.getId());

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(URI_ONE, personDTO.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isAccepted()).andReturn();

        verify(personService, times(1)).delete(any(Long.class));
    }
}
