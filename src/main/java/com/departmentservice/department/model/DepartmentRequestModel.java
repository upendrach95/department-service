package com.departmentservice.department.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequestModel {

    @JsonProperty("name")
    @NotBlank(message = "Name is mandatory")
    @Size(min=2 , message = "Name cannot be less than 2 characters")
    private String name;

    @JsonProperty("city")
    @NotBlank(message = "City is mandatory")
    @Size(min=2 , message = "City cannot be less than 2 characters")
    private String city;

    @JsonProperty("state")
    @NotBlank(message = "State is mandatory")
    @Size(min = 2 , message = "State cannot be less than 2 characters")
    private String state;

    @JsonProperty("country")
    @NotBlank(message = "Country is mandatory")
    @Size(min = 2, message = "Country cannot be less than 2 characters")
    private String country;

    @JsonProperty("zipCode")
    @NotBlank(message = "Zipcode is mandatory")
    @Size(min = 5, message = "Zipcode cannot be less than 5 characters")
    private String zipCode;


}
