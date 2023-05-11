package com.departmentservice.department.Controller;

import com.departmentservice.department.exception.NotFoundException;
import com.departmentservice.department.model.DepartmentRequestModel;
import com.departmentservice.department.model.DepartmentResponseModel;
import com.departmentservice.department.service.DepartmentService;
import com.departmentservice.department.shared.DepartmentDto;
import com.departmentservice.department.shared.Utils;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<List<DepartmentResponseModel>> searchDepartments(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "country", required = false) String country){
        DepartmentDto department = null;
        if(name!=null) {
            department= departmentService.getDepartmentByName(name);
        }

        if(department==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        List<DepartmentResponseModel> departmentsList = new ArrayList<>();


        DepartmentResponseModel departmentResponseModel = modelMapper.map(department, DepartmentResponseModel.class);
        departmentsList.add(departmentResponseModel);


        return new ResponseEntity<>(departmentsList, HttpStatus.OK);
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
        if(departmentService.getDepartmentByName(name) == null){
            throw new ValidationException("Failed to delete, department not found");
        }
        boolean res = departmentService.deleteDepartmentByName(name);
        if(res){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        throw new ValidationException("An error occurred while executing request");
    }

}

