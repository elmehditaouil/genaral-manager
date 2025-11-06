package com.manager.general.service;

import com.manager.general.entity.Employee;
import com.manager.general.entity.Person;
import com.manager.general.repository.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link PersonService} that handles business logic
 * for {@link Person} entities.
 */
@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;


    /**
     * Retrieves all employees.
     *
     * @return a {@link List} of all {@link Employee} entities
     */
    @Override
    public Page<Person> getAllPersons(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    /**
     * Finds a person by their ID.
     *
     * @param id the ID of the person
     * @return an {@link Optional} containing the {@link Person} if found, otherwise empty
     */
    @Override
    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    /**
     * Saves a new person or updates an existing one.
     *
     * @param person the {@link Person} entity to save or update
     * @return the saved {@link Person} entity
     */
    @Override
    @Transactional
    public Person saveOrUpdate(Person person) {
        return personRepository.save(person);
    }

    /**
     * Deletes a person by their ID.
     *
     * @param id the ID of the person to delete
     */
    @Transactional
    @Override
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<Person> findAllPersons(Specification<Person> specification) {
        return personRepository.findAll(specification);
    }
}
