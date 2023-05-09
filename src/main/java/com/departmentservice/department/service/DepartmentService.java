package com.departmentservice.department.service;

import com.departmentservice.department.shared.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    DepartmentDto createDepartment(DepartmentDto DepartmentDetails);

    List<DepartmentDto> getAllDepartments();

    DepartmentDto getDepartmentById(long id);

    DepartmentDto getDepartmentByName(String name);

    boolean deleteDepartmentByName(String name);

    List<DepartmentDto> getDepartmentByState(String state);

    List<DepartmentDto> getDepartmentByCountry(String country);

    List<DepartmentDto> getDepartmentByCity(String city);

    List<DepartmentDto> getDepartmentByZipCode(Integer zipcode);
}
