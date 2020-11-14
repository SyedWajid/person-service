package com.mic3.personservice.repository;

import com.mic3.personservice.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.mic3.personservice.util.TestPersonUtil.getDefaultPerson;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class PersonRepositoryTest {
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    PersonRepository personRepository;

    @Test
    public void personCreateTest(){
        Person personDTO = getDefaultPerson();
        Person savedPerson = this.personRepository.save(personDTO);
        savedPerson = this.personRepository.getOne(savedPerson.getId());
        assertEquals(personDTO.getName(), savedPerson.getName());
    }

    @Test
    public void personsListTest(){
        Person person = getDefaultPerson();
        person = entityManager.persist(person);
        Page<Person> result = this.personRepository.findAll(PageRequest.of(0, 10));
        assertEquals(1, result.getTotalElements());
        assertEquals(person.getId(), result.get().findFirst().get().getId());
        assertEquals(person.getName(), result.get().findFirst().get().getName());
    }

    @Test
    public void personDeleteTest(){
        Person person = getDefaultPerson();
        person = entityManager.persist(person);

        this.personRepository.delete(person);

        Optional<Person> personOptional = this.personRepository.findById(person.getId());
        assertEquals(false, personOptional.isPresent());
    }
}
