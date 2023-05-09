package com.departmentservice.department.Controller;

import com.departmentservice.department.model.DepartmentRequestModel;
import com.departmentservice.department.model.DepartmentResponseModel;
import com.departmentservice.department.service.DepartmentService;
import com.departmentservice.department.shared.DepartmentDto;
import jakarta.validation.Valid;
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

    @GetMapping
    public ResponseEntity<List<DepartmentResponseModel>> getAllDepartments() {
        List<DepartmentDto> departments = departmentService.getAllDepartments();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<DepartmentResponseModel> departmentList = new ArrayList<>();
        departments.forEach((departmentDto ->  {
            DepartmentResponseModel departmentResponseModel = modelMapper.map(departmentDto, DepartmentResponseModel.class);
            departmentList.add(departmentResponseModel);
        }));

        if(departmentList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(departmentList, HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<DepartmentResponseModel> getDepartmentById(@PathVariable Long id){
        DepartmentDto departmentDto = departmentService.getDepartmentById(id);
        if(departmentDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        DepartmentResponseModel returnValue = modelMapper.map(departmentDto, DepartmentResponseModel.class);

        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<DepartmentResponseModel> getDepartmentByName(@PathVariable String name){
        DepartmentDto departmentDto = departmentService.getDepartmentByName(name);

        if(departmentDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        DepartmentResponseModel returnValue = modelMapper.map(departmentDto, DepartmentResponseModel.class);
        return  new ResponseEntity<>(returnValue, HttpStatus.OK);

    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DepartmentResponseModel> createDepartment(@Valid @RequestBody DepartmentRequestModel departmentDetails){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        DepartmentDto departmentDto = modelMapper.map(departmentDetails, DepartmentDto.class);

        DepartmentDto createdValue = departmentService.createDepartment(departmentDto);
        DepartmentResponseModel returnValue = modelMapper.map(createdValue, DepartmentResponseModel.class);

        return new ResponseEntity<>(returnValue, HttpStatus.CREATED);
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
    ResponseEntity<DepartmentResponseModel> updateDepartment(@Valid @RequestBody DepartmentRequestModel departmentDetails){
        String name = departmentDetails.getName();
        if(departmentService.getDepartmentByName(name)==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        DepartmentDto existingDepartment = departmentService.getDepartmentByName(name);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        DepartmentDto departmentDto = modelMapper.map(departmentDetails,DepartmentDto.class);
        departmentDto.setId(existingDepartment.getId());

        DepartmentDto createdValue = departmentService.createDepartment(departmentDto);

        DepartmentResponseModel returnValue = modelMapper.map(createdValue,DepartmentResponseModel.class);

        return new ResponseEntity<>(returnValue, HttpStatus.OK);

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

