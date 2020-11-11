package com.mic3.personservice.service;

import com.mic3.personservice.domain.Person;
import com.mic3.personservice.dto.PersonDTO;
import com.mic3.personservice.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Syed Wajid
 *
 * Service for person entity, it uses person repository for its operations
 */
@Service
@AllArgsConstructor
@Transactional//(readonly=true)
public class PersonServiceImpl implements  IPersonService{

    /**
     * PersonRepository reference
     */
    private PersonRepository personRepository;

    private ModelMapper modelMapper;

    /**
     * Return page of person objects based on query parameters
     * Using chache
     * @param pageable
     * @return
     */
    /*@Cacheable(
            cacheNames = "persons2",
            unless = "#result == null",
            key = "#pageable"
    )*/
    public Page<PersonDTO> getPersons(Pageable pageable){
        return this.personRepository.findAll(pageable).map(person ->
                toDTO(person)
        );
    }

    public PersonDTO createPerson(PersonDTO person){
        Person personEntity = toEntity(person);
        personEntity = this.personRepository.save(personEntity);
        return toDTO(personEntity);
    }

    @Override
    public PersonDTO loadPerson(long personId) {
        Optional<Person> person = this.personRepository.findById(personId);
        if(person.isPresent()){
            return toDTO(person.get());
        }
        throw new PersonNotFoundException(personId);
    }

    @Override
    public PersonDTO updatePerson(long personId, PersonDTO person) {
        Optional<Person> personOptional = this.personRepository.findById(personId);
        if(personOptional.isPresent()){
            person.setId(personId);
            Person personBean = this.personRepository.save(toEntity(person));
            return toDTO(personBean);
        }
        throw new PersonNotFoundException(personId);
    }

    @Override
    public void deletePerson(long personId) {
        Optional<Person> personOptional = this.personRepository.findById(personId);
        if(personOptional.isPresent()){
            this.personRepository.delete(personOptional.get());
        }
        throw new PersonNotFoundException(personId);
    }

    private Person toEntity(PersonDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }

    private PersonDTO toDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }
}
