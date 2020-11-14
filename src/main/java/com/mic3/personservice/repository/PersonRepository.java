package com.mic3.personservice.repository;

import com.mic3.personservice.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Syed Wajid
 * JPA repository for person
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
