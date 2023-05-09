package com.departmentservice.department.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

    private int id;
    private String name;
    private String city;
    private String state;
    private String country;
    private String zipCode;

}
