package com.departmentservice.department.service;

import com.departmentservice.department.data.DepartmentEntity;
import com.departmentservice.department.data.DepartmentRepository;
import com.departmentservice.department.shared.DepartmentDto;
import com.departmentservice.department.shared.Utils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

    DepartmentRepository departmentRepository;
    Utils utils;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository , Utils utils){
        this.departmentRepository = departmentRepository;
        this.utils = utils;
    }

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        DepartmentEntity departmentEntity =modelMapper.map(departmentDto, DepartmentEntity.class);
        departmentRepository.save(departmentEntity);
        return modelMapper.map(departmentEntity, DepartmentDto.class);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        Iterable<DepartmentEntity> departments = departmentRepository.findAll();

        return utils.getDepartmentDtoList(departments);
    }

    @Override
    public DepartmentDto getDepartmentById(long id){
        Optional<DepartmentEntity> departmentEntity= departmentRepository.findById(id);
        return  new ModelMapper().map(departmentEntity, DepartmentDto.class);
    }

    @Override
    public DepartmentDto getDepartmentByName(String name) {
        DepartmentEntity department= departmentRepository.findByName(name);
        if (department==null){
            return  null;
        }else{
            return new ModelMapper().map(department,DepartmentDto.class);
        }
    }


    public List<DepartmentDto> getDepartmentByCity(String city) {
        Iterable<DepartmentEntity> departments = departmentRepository.findByCity(city);

        return utils.getDepartmentDtoList(departments);
    }

    @Override
    public List<DepartmentDto> getDepartmentByState(String state) {
        Iterable<DepartmentEntity> departments = departmentRepository.findByState(state);

        return utils.getDepartmentDtoList(departments);
    }

    @Override
    public List<DepartmentDto> getDepartmentByCountry(String country){
        List<DepartmentDto> allDepartments= new ArrayList<>();
        Iterable<DepartmentEntity> departments = departmentRepository.findByCountry(country);
        ModelMapper modelMapper = new ModelMapper();
        long count = StreamSupport.stream(departments.spliterator(),false).count();
        if(count > 0){
            departments.forEach(departmentEntity -> {
                DepartmentDto departmentDto = modelMapper.map(departmentEntity,DepartmentDto.class );
                allDepartments.add(departmentDto);
            });
        }

        return allDepartments;
    }

    @Override
    public List<DepartmentDto> getDepartmentByZipCode(Integer zipcode){
        List<DepartmentDto> allDepartments= new ArrayList<>();
        Iterable<DepartmentEntity> departments = departmentRepository.findByZipCode(zipcode);
        ModelMapper modelMapper = new ModelMapper();
        long count = StreamSupport.stream(departments.spliterator(),false).count();
        if(count > 0){
            departments.forEach(departmentEntity -> {
                DepartmentDto departmentDto = modelMapper.map(departmentEntity,DepartmentDto.class );
                allDepartments.add(departmentDto);
            });
        }

        return allDepartments;
    }

    @Override
    public boolean deleteDepartmentByName(String name){
        try{
            departmentRepository.deleteByName(name);
            return true;

        }catch(Exception ex){
            return false;
        }
    }

}