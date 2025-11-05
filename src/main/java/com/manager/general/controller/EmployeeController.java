    package com.manager.general.controller;

    import com.manager.general.dto.EmployeeDto;
    import com.manager.general.entity.Employee;
    import com.manager.general.mapper.EmployeeMapper;
    import com.manager.general.service.EmployeeService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api/employees")
    @RequiredArgsConstructor
    public class EmployeeController {

        private final EmployeeService employeeService;
        private final EmployeeMapper employeeMapper;


        /**
         * Retrieves all employees with pagination.
         * Requires the user to have the "ROLE_USER" authority.
         *
         * @param page the page number to retrieve (0-based), defaults to 0
         * @param size the number of employees per page, defaults to 10
         * @return a ResponseEntity containing a Page of {@link EmployeeDto} objects
         */
        @GetMapping
        @PreAuthorize("hasAuthority('ROLE_USER')")
        public ResponseEntity<Page<EmployeeDto>> getAllEmployees(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size) {

            Pageable pageable = PageRequest.of(page, size);
            Page<Employee> employeePage = employeeService.getAllEmployees(pageable);

            Page<EmployeeDto> dtoPage = employeePage.map(employeeMapper::toDTO);

            return ResponseEntity.ok(dtoPage);
        }

        /**
         * Creates a new employee.
         *
         * @param employeeDto the {@link EmployeeDto} containing the employee information
         * @return a ResponseEntity containing the created {@link EmployeeDto} with HTTP status 201 (Created)
         */
        @PostMapping
        public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto) {
            Employee employee = employeeMapper.toEmployee(employeeDto);
            Employee saved = employeeService.createEmployee(employee);
            EmployeeDto savedDto = employeeMapper.toDTO(saved);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
        }
    }
