package com.manager.general.repository;

import com.manager.general.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Override
    List<Person> findAll();

    @Override
    Person save(Person person);

    @Override
    void deleteById(Long id);

    @Override
    Optional<Person> findById(Long id);

}
