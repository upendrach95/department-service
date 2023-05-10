package com.departmentservice.department.shared;

import com.departmentservice.department.data.DepartmentEntity;
import com.departmentservice.department.exception.NotFoundException;
import com.departmentservice.department.model.DepartmentRequestModel;
import com.departmentservice.department.model.DepartmentResponseModel;
import jakarta.validation.ValidationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class Utils {

    public List<DepartmentResponseModel> getDepartmentResponseModelList(List<DepartmentDto> departments) throws NotFoundException {

        if (departments == null || departments.isEmpty()) {
            throw new NotFoundException("0 records found");
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<DepartmentResponseModel> departmentList = new ArrayList<>();
        departments.forEach((departmentDto -> {
            DepartmentResponseModel departmentResponseModel = modelMapper.map(departmentDto, DepartmentResponseModel.class);
            departmentList.add(departmentResponseModel);
        }));

        return departmentList;
    }

    public DepartmentResponseModel getDepartmentResponseModel(DepartmentDto departmentDto) throws NotFoundException {
        if (departmentDto == null) {
            throw new NotFoundException("Department not found");
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper.map(departmentDto, DepartmentResponseModel.class);
    }

    public DepartmentDto getDepartmentDto(DepartmentRequestModel departmentDetails) throws NotFoundException {
        if(departmentDetails == null){
            throw new NotFoundException("Request object is null");
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(departmentDetails, DepartmentDto.class);
    }

    public List<DepartmentDto> getDepartmentDtoList(Iterable<DepartmentEntity> departments){
        List<DepartmentDto> allDepartments = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        long count = StreamSupport.stream(departments.spliterator(), false).count();
        if(count > 0){
            departments.forEach(departmentEntity -> {
                DepartmentDto departmentDto = modelMapper.map(departmentEntity, DepartmentDto.class);
                allDepartments.add(departmentDto);
            });
        }else{
            throw new ValidationException("List of entities is null or empty");
        }

        return allDepartments;
    }
}
