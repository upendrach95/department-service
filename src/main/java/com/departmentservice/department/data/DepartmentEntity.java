package com.departmentservice.department.data;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false , length = 100)
    private String name;

    @Column(nullable = false , length = 50)
    private String city;

    @Column(nullable = false , length = 50)
    private String state;

    @Column(nullable = false , length = 50)
    private String country;

    @Column(nullable = false , length = 10)
    private String zipCode;
}
