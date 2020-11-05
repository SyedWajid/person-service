package com.mic3.personservice.service;

import com.mic3.personservice.domain.Person;
import com.mic3.personservice.repository.PersonRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Syed Wajid
 *
 * Service for person entity, it uses person repository for its operations
 */
@Service
@Transactional//(readonly=true)
public class PersonServiceImpl implements  IPersonService{

    /**
     * PersonRepository reference
     */
    private PersonRepository personRepository;

    /**
     * Using constructor injection
     * @param personRepository
     */
    public PersonServiceImpl(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    /**
     * Return page of person objects based on query parameters
     * Using chaching
     * @param pageable
     * @return
     */
    @Cacheable(
            cacheNames = "persons",
            unless = "#result == null"
    )
    public Page<Person> getPersons(Pageable pageable){
        return this.personRepository.findAll(pageable);//.stream().map(person -> new Person()).collect(Collectors.toList());
    }

    public void add(Person person){
        this.personRepository.save(person);
    }
}
