package com.manager.general.service;

import com.manager.general.entity.Employee;
import com.manager.general.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link EmployeeService} that handles business logic
 * for {@link Employee} entities.
 */
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    /**
     * Retrieves an employee by its ID.
     *
     * @param id the ID of the employee
     * @return an {@link Optional} containing the found {@link Employee}, or empty if not found
     */
    @Override
    public Optional<Employee> getEmployee(Long id) {
        return employeeRepository.findById(id);
    }

    /**
     * Retrieves all employees.
     *
     * @return a {@link List} of all {@link Employee} entities
     */
    @Override
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }
    /**
     * Creates a new employee or updates an existing one.
     *
     * @param employee the {@link Employee} entity to save
     * @return the saved {@link Employee} entity
     */
    @Override
    @Transactional
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * Deletes a employee by their ID.
     *
     * @param id the ID of the person to delete
     */
    @Transactional
    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }
}
