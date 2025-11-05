package com.manager.general.mapper;

import com.manager.general.dto.PersonDto;
import com.manager.general.entity.Person;
import java.util.List;
import java.util.stream.Collectors;

public class PersonMapper {

    public  static PersonDto toDto(Person person) {
        if (person == null) return null;
        return new PersonDto(person.getId(), person.getFirstName(), person.getLastName());
    }

    public static Person toEntity(PersonDto dto) {
        if (dto == null) return null;
        return new Person(dto.id(), dto.firstName(), dto.lastName());
    }

    public static List<PersonDto> toDTOList(List<Person> person) {
        return person.stream()
                .map(PersonMapper::toDto)
                .collect(Collectors.toList());
    }
}
