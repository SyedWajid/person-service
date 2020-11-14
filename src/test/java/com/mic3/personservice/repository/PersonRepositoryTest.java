package com.mic3.personservice.repository;

import com.mic3.personservice.domain.Person;
import com.mic3.personservice.dto.PersonDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


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
        assertThat(savedPerson.getName()).isEqualTo(personDTO.getName());
    }

    @Test
    public void personsListTest(){
        Person person = getDefaultPerson();
        person = entityManager.persist(person);
        Page<Person> result = this.personRepository.findAll(PageRequest.of(0, 10));
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.get().findFirst().get().getId()).isEqualTo(person.getId());
        assertThat(result.get().findFirst().get().getName()).isEqualTo(person.getName());
    }

    @Test
    public void personDeleteTest(){
        Person person = getDefaultPerson();
        person = entityManager.persist(person);

        this.personRepository.delete(person);

        Optional<Person> personOptional = this.personRepository.findById(person.getId());
        assertThat(personOptional.isPresent()).isFalse();
    }

    private Person getDefaultPerson(){
        Person personDTO = new Person();
        personDTO.setName("Syed Wajid");
        personDTO.setAge(32);
        return personDTO;
    }
}
