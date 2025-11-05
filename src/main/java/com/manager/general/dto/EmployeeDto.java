package com.manager.general.dto;

// UserDTO.java
public record EmployeeDto(
        Long id,
        String firstName,
        String lastName,
        String email
) {}
