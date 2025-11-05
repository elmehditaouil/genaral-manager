package com.manager.general.service;

import com.manager.general.entity.Employee;
import com.manager.general.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    Page<Person> getAllPersons(Pageable pageable);
    Optional<Person> findById(Long id);
    Person saveOrUpdate(Person person);
    void deleteById(Long id);
}
