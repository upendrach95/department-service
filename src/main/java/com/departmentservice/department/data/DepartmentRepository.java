package com.departmentservice.department.data;

import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<DepartmentEntity, Long> {


    DepartmentEntity findByName(String name);

    void deleteByName(String name);

    Iterable<DepartmentEntity> findByCity(String name);

    Iterable<DepartmentEntity> findByState(String state);

    Iterable<DepartmentEntity> findByCountry(String country);

    Iterable<DepartmentEntity> findByZipCode(Integer zipCode);



}
