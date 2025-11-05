package com.manager.general.repository;

import com.manager.general.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Override
    List<Employee> findAll();
    @Override
    Optional<Employee> findById(Long id);
    @Override
    Employee save(Employee employee);
    @Override
    void delete(Employee employee);

}
