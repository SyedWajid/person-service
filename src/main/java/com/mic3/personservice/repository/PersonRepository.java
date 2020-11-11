package com.mic3.personservice.repository;

import com.mic3.personservice.domain.Person;
import com.mic3.personservice.dto.PersonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Syed Wajid
 * JPA repository for person
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
