package com.mic3.personservice.service;

import com.mic3.personservice.domain.Person;
import com.mic3.personservice.repository.PersonRepository;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PersonServiceImpl implements  IPersonService{
    private PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @Cacheable(
            cacheNames = "persons",
            unless = "#result == null"
    )
    public List<Person> getPersons(){
        return (List<Person>) this.personRepository.findAll();
    }

    public void add(Person person){
        this.personRepository.save(person);
    }
}
