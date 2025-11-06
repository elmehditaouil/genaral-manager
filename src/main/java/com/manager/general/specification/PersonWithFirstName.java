package com.manager.general.specification;

import com.manager.general.entity.Person;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public record PersonWithFirstName(String firstName)  implements Specification<Person> {


    @Override
    public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (Objects.isNull(firstName) || firstName.isBlank()) {
            return cb.conjunction();
        }
        return cb.equal(
                cb.upper(root.get("firstName")),
                firstName.toUpperCase()
        );
    }
}
