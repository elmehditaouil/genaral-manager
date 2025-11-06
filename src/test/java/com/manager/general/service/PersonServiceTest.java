package com.manager.general.service;

import com.manager.general.entity.Person;
import com.manager.general.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void shouldReturnAllPersons() {
        // given
        Person p1 = new Person(1L, "John", "Doe");
        Person p2 = new Person(2L, "Jane", "Smith");
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        Page<Person> page = new PageImpl<>(List.of(p1, p2), pageable, 2);

        when(personRepository.findAll(pageable)).thenReturn(page);

        // when
        Page<Person> persons = personService.getAllPersons(pageable);

        // then
        assertThat(persons.getContent())
                .hasSize(2)
                .containsExactly(p1, p2);
    }

    @Test
    void shouldReturnPersonById() {
        Optional<Person> p = Optional.of(new Person(1L, "John", "Doe"));
        when(personRepository.findById(1L)).thenReturn(p);

        Optional<Person> person = personService.findById(1L);

        assertThat(person).isEqualTo(p);
    }

    @Test
    void shouldReturnEmptyOptionalWhenPersonNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Person> result = personService.findById(1L);
        assertTrue(result.isEmpty());
    }


    @Test
    void shouldReturnPersonOnSaveOrUpdate() {
        Person p = new Person(1L, "John", "Doe");
        when(personRepository.save(p)).thenReturn(p);

        Person person = personService.saveOrUpdate(p);

        assertThat(person).isEqualTo(p);
    }

}
