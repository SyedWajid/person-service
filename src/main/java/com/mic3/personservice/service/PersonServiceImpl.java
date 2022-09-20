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

import javax.persistence.Cacheable;
import java.util.Optional;

/**
 * @author Syed Wajid
 *
 * Service for person entity, it uses person repository for its operations
 */
@Service
@AllArgsConstructor
@Transactional//(readonly=true)
@Cacheable
public class PersonServiceImpl implements  IPersonService{

    /**
     * PersonRepository reference
     */
    private PersonRepository personRepository;

    private ModelMapper modelMapper;

    public Page<PersonDTO> list(Pageable pageable){
        return this.personRepository.findAll(pageable).map(this::toDTO);
    }

    public PersonDTO create(PersonDTO person){
        Person personEntity = toEntity(person);
        personEntity = this.personRepository.save(personEntity);
        return toDTO(personEntity);
    }

    @Override
    public PersonDTO findById(long personId) {
        Optional<Person> personOptional = this.personRepository.findById(personId);
        return personOptional.map(this::toDTO)
                .orElseThrow(()->new PersonNotFoundException(personId));
    }

    @Override
    public PersonDTO update(long personId, PersonDTO person) {
        Optional<Person> personOptional = this.personRepository.findById(personId);
        return personOptional.map(p->{
            person.setId(personId);
            Person personBean = this.personRepository.save(toEntity(person));
            return toDTO(personBean);
        }).orElseThrow(()-> new PersonNotFoundException(personId));
    }

    @Override
    public void delete(long personId) {
        Optional<Person> personOptional = this.personRepository.findById(personId);
        personOptional.map(p-> {
            this.personRepository.delete(p);
            return p;
        }).orElseThrow(()->new PersonNotFoundException(personId));
        return;
    }

    private Person toEntity(PersonDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }

    private PersonDTO toDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }
}
