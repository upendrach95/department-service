package com.departmentservice.department.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/department-service")
public class Controller {
    @GetMapping
    public String checkingDepartmentService(){
        return "The department service is working";
    }
}
