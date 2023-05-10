package com.departmentservice.department.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldExceptionMessage {
    private String field;
    private String message;
}
