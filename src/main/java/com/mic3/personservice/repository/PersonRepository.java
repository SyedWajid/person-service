package com.mic3.personservice.repository;

import com.mic3.personservice.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
