package com.manager.general.controller;

import com.manager.general.dto.PersonDto;
import com.manager.general.entity.Person;
import com.manager.general.exception.PersonNotFoundException;
import com.manager.general.mapper.PersonMapper;
import com.manager.general.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link Person} entities.
 * Provides CRUD operations for persons in the system.
 */
@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {




        private final PersonService personService;

    /**
     * Retrieves all persons with optional pagination.
     *
     * @param page the page number to retrieve (0-based), defaults to 0
     * @param size the number of persons per page, defaults to 10
     * @return a ResponseEntity containing a Page of {@link PersonDto} objects
     */
    @GetMapping
    public ResponseEntity<Page<PersonDto>> getAllPersons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Person> personPage = personService.getAllPersons(pageable); // Il faut adapter le service pour Page

        // Convert entities to DTOs
        Page<PersonDto> dtoPage = personPage.map(PersonMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

        /**
         * Creates a new person.
         *
         * @param person the {@link Person} object to create
         * @return a ResponseEntity containing the created {@link PersonDto} with HTTP status 201 (Created)
         */
        @PostMapping
        public ResponseEntity<PersonDto> createPerson(@RequestBody Person person) {
            Person createdPerson = personService.saveOrUpdate(person);
            return new ResponseEntity<>(PersonMapper.toDto(createdPerson), HttpStatus.CREATED);
        }

        /**
         * Retrieves a person by their ID.
         *
         * @param id the ID of the person to retrieve
         * @return a ResponseEntity containing the {@link PersonDto} of the found person
         * @throws PersonNotFoundException if the person with the given ID does not exist
         */
        @GetMapping("/{id}")
        public ResponseEntity<PersonDto> getPersonById(@PathVariable Long id) {
            Person person = personService.findById(id)
                    .orElseThrow(() -> new PersonNotFoundException("Person Not Found"));

            return ResponseEntity.ok(PersonMapper.toDto(person));
        }

        /**
         * Updates an existing person by their ID.
         *
         * @param id            the ID of the person to update
         * @param personDetails the {@link Person} object containing updated information
         * @return a ResponseEntity containing the updated {@link PersonDto}
         * @throws PersonNotFoundException if the person with the given ID does not exist
         */
        @PutMapping("/{id}")
        public ResponseEntity<PersonDto> updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
            Person existingPerson = personService.findById(id)
                    .orElseThrow(() -> new PersonNotFoundException("Person with ID " + id + " not found"));

            existingPerson.setFirstName(personDetails.getFirstName());
            existingPerson.setLastName(personDetails.getLastName());
            // Add other fields if needed

            Person updatedPerson = personService.saveOrUpdate(existingPerson);
            return ResponseEntity.ok(PersonMapper.toDto(updatedPerson));
        }

        /**
         * Deletes a person by their ID.
         *
         * @param id the ID of the person to delete
         * @return a ResponseEntity with HTTP status 204 (No Content)
         * @throws PersonNotFoundException if the person with the given ID does not exist
         */
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
            Person existingPerson = personService.findById(id)
                    .orElseThrow(() -> new PersonNotFoundException("Person with ID " + id + " not found"));

            personService.deleteById(existingPerson.getId());
            return ResponseEntity.noContent().build();
        }


}
