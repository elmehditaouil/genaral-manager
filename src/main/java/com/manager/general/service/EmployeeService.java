package com.manager.general.service;

import com.manager.general.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

     Optional<Employee> getEmployee(Long id);

    Page<Employee> getAllEmployees(Pageable pageable);


     Employee createEmployee(Employee employee);
}
