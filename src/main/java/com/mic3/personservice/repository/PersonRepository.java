package com.mic3.personservice.repository;

import com.mic3.personservice.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Syed Wajid
 * JPA repository for person
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
}
