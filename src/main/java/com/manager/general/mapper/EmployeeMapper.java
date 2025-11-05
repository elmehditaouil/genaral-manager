package com.manager.general.mapper;


import com.manager.general.dto.EmployeeDto;
import com.manager.general.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper for Employee entity and DTO.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDto toDTO(Employee employee);

    Employee toEmployee(EmployeeDto dto);

    List<EmployeeDto> toDTOList(List<Employee> employees);
}
