package com.departmentservice.department.Controller;

import com.departmentservice.department.exception.NotFoundException;
import com.departmentservice.department.model.DepartmentRequestModel;
import com.departmentservice.department.model.DepartmentResponseModel;
import com.departmentservice.department.service.DepartmentService;
import com.departmentservice.department.shared.DepartmentDto;
import com.departmentservice.department.shared.Utils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    Utils utils;

    @GetMapping
    public ResponseEntity<List<DepartmentResponseModel>> getAllDepartments() throws NotFoundException {
        List<DepartmentDto> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(utils.getDepartmentResponseModelList(departments), HttpStatus.OK);

    }

    @GetMapping("id/{id}")
    public ResponseEntity<DepartmentResponseModel> getDepartmentById(@PathVariable Long id) throws NotFoundException {
        DepartmentDto departmentDto = departmentService.getDepartmentById(id);

        return new ResponseEntity<>(utils.getDepartmentResponseModel(departmentDto), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<DepartmentResponseModel> getDepartmentByName(@PathVariable String name) throws NotFoundException {
        DepartmentDto departmentDto = departmentService.getDepartmentByName(name);

        if(departmentDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(utils.getDepartmentResponseModel(departmentDto), HttpStatus.OK);
    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DepartmentResponseModel> createDepartment(@Valid @RequestBody DepartmentRequestModel departmentDetails) throws NotFoundException {
        DepartmentDto departmentDto = utils.getDepartmentDto(departmentDetails);

        DepartmentDto createdValue = departmentService.createDepartment(departmentDto);

        return new ResponseEntity<>(utils.getDepartmentResponseModel(createdValue), HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DepartmentResponseModel>> searchEmployees(
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "city", required = false) String city) throws NotFoundException {
        List<DepartmentDto> departments = null;
        if(state != null && city != null){
            departments = departmentService.getDepartmentByState(state);
        }else if(state != null){
            departments = departmentService.getDepartmentByCity(city);
        }else if(city != null){
            departments = departmentService.getDepartmentByCity(city);
        }
        return new ResponseEntity<>(utils.getDepartmentResponseModelList(departments), HttpStatus.OK);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<DepartmentResponseModel> updateDepartment(@Valid @RequestBody DepartmentRequestModel departmentDetails) throws NotFoundException {
        String name = departmentDetails.getName();
        if(departmentService.getDepartmentByName(name)==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        DepartmentDto existingDepartment = departmentService.getDepartmentByName(name);

        DepartmentDto departmentDto = utils.getDepartmentDto(departmentDetails);
        departmentDto.setId(existingDepartment.getId());

        DepartmentDto createdValue = departmentService.createDepartment(departmentDto);

        return new ResponseEntity<>(utils.getDepartmentResponseModel(createdValue), HttpStatus.OK);

    }

    @DeleteMapping("/{name}")
    ResponseEntity<Void> delete (@PathVariable String name){
        if ( departmentService.getDepartmentByName(name) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean res = departmentService.deleteDepartmentByName(name);
        if(res){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

