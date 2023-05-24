package com.departmentservice.department;

import com.departmentservice.department.data.DepartmentEntity;
import com.departmentservice.department.data.DepartmentRepository;
import com.departmentservice.department.service.DepartmentServiceImpl;
import com.departmentservice.department.shared.DepartmentDto;
import com.departmentservice.department.shared.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceTest {

    @InjectMocks
    DepartmentServiceImpl departmentService;

    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    Utils utils;

    @Test
    public void getAllDepartmentsTest(){
        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");
        DepartmentDto departmentDto2 = new DepartmentDto(2, "name2","city2","state2","country2","zipcode2");
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        departmentDtoList.add(departmentDto);
        departmentDtoList.add(departmentDto2);

        DepartmentEntity departmentEntity = new DepartmentEntity(1, "name","city","state","country","zip code");
        DepartmentEntity departmentEntity1 = new DepartmentEntity(1, "name","city","state","country","zip code");
        Iterable<DepartmentEntity> departmentEntities = Arrays.asList(departmentEntity, departmentEntity1);

        when(departmentRepository.findAll()).thenReturn(departmentEntities);
        when(departmentService.getAllDepartments()).thenReturn(departmentDtoList);
        when(utils.getDepartmentDtoList(departmentEntities)).thenReturn(departmentDtoList);

        List<DepartmentDto> departmentDtos = departmentService.getAllDepartments();

        assertThat(departmentDto.getName()).isEqualTo(departmentDto.getName());
        assertThat(departmentDto.getCity()).isEqualTo(departmentDto.getCity());
        assertThat(departmentDto.getState()).isEqualTo(departmentDto.getState());
        assertThat(departmentDto.getCountry()).isEqualTo(departmentDto.getCountry());
        assertThat(departmentDto.getZipCode()).isEqualTo(departmentDto.getZipCode());
    }

    @Test
    public void getDepartmentByName(){

        DepartmentEntity departmentEntity = new DepartmentEntity(1, "name","city","state","country","zip code");

        String name = "name";

        when(departmentRepository.findByName(name)).thenReturn(departmentEntity);

        DepartmentDto departmentDtos = departmentService.getDepartmentByName(name);

        assertThat(departmentDtos.getName()).isEqualTo(departmentEntity.getName());
        assertThat(departmentDtos.getCity()).isEqualTo(departmentEntity.getCity());
        assertThat(departmentDtos.getState()).isEqualTo(departmentEntity.getState());
        assertThat(departmentDtos.getCountry()).isEqualTo(departmentEntity.getCountry());
        assertThat(departmentDtos.getZipCode()).isEqualTo(departmentEntity.getZipCode());

    }

    @Test
    public void getDepartmentNameNullTest(){

        DepartmentEntity departmentEntity = null;

        String name = "name";

        when(departmentRepository.findByName(name)).thenReturn(departmentEntity);

        DepartmentDto departmentDto = departmentService.getDepartmentByName(name);

        assertThat(departmentDto).isNull();

    }

    @Test
    public void getDepartmentByCity(){

        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");
        DepartmentDto departmentDto2 = new DepartmentDto(2, "name2","city2","state2","country2","zipcode2");
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        departmentDtoList.add(departmentDto);
        departmentDtoList.add(departmentDto2);

        DepartmentEntity departmentEntity = new DepartmentEntity(1, "name","city","state","country","zip code");
        DepartmentEntity departmentEntity1 = new DepartmentEntity(1, "name","city","state","country","zip code");
        Iterable<DepartmentEntity> departmentEntities = Arrays.asList(departmentEntity, departmentEntity1);

        String city = "city";
        when(departmentRepository.findByCity(city)).thenReturn(departmentEntities);
        when(departmentService.getDepartmentByCity(city)).thenReturn(departmentDtoList);
        when(utils.getDepartmentDtoList(departmentEntities)).thenReturn(departmentDtoList);

        List<DepartmentDto> departmentDtos = departmentService.getDepartmentByCity(city);

        assertThat(departmentDtos.get(0).getName()).isEqualTo(departmentDto.getName());
        assertThat(departmentDtos.get(0).getCity()).isEqualTo(departmentDto.getCity());
        assertThat(departmentDtos.get(0).getState()).isEqualTo(departmentDto.getState());
        assertThat(departmentDtos.get(0).getCountry()).isEqualTo(departmentDto.getCountry());
        assertThat(departmentDtos.get(0).getZipCode()).isEqualTo(departmentDto.getZipCode());

    }

    @Test
    public void getDepartmentByState(){

        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zipcode");
        DepartmentDto departmentDto2 = new DepartmentDto(2, "name2","city2","state2","country2","zipcode2");
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        departmentDtoList.add(departmentDto);
        departmentDtoList.add(departmentDto2);

        DepartmentEntity departmentEntity = new DepartmentEntity(1, "name","city","state","country","zip code");
        DepartmentEntity departmentEntity1 = new DepartmentEntity(1, "name","city","state","country","zip code");
        Iterable<DepartmentEntity> departmentEntities = Arrays.asList(departmentEntity, departmentEntity1);

        String state = "state";
        when(departmentRepository.findByState(state)).thenReturn(departmentEntities);
        when(departmentService.getDepartmentByState(state)).thenReturn(departmentDtoList);
        when(utils.getDepartmentDtoList(departmentEntities)).thenReturn(departmentDtoList);

        List<DepartmentDto> departmentDtos = departmentService.getDepartmentByState(state);

        assertThat(departmentDtos.get(0).getName()).isEqualTo(departmentDto.getName());
        assertThat(departmentDtos.get(0).getCity()).isEqualTo(departmentDto.getCity());
        assertThat(departmentDtos.get(0).getState()).isEqualTo(departmentDto.getState());
        assertThat(departmentDtos.get(0).getCountry()).isEqualTo(departmentDto.getCountry());
        assertThat(departmentDtos.get(0).getZipCode()).isEqualTo(departmentDto.getZipCode());

    }

    @Test
    public void getDepartmentByStateAndCity(){
        DepartmentDto departmentDto1 = new DepartmentDto(1, "name","city","state","country","zipcode");
        DepartmentDto departmentDto = new DepartmentDto(2, "name2","city2","state2","country2","zipcode2");

        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        departmentDtoList.add(departmentDto1);
        departmentDtoList.add(departmentDto);

        DepartmentEntity departmentEntity = new DepartmentEntity(1, "name","city","state","country","zip code");
        DepartmentEntity departmentEntity1 = new DepartmentEntity(1, "name","city","state","country","zip code");
        Iterable<DepartmentEntity> departmentEntities = Arrays.asList(departmentEntity, departmentEntity1);

        String state = "state";
        String city = "city";

        when(departmentRepository.findByStateAndCity(state, city)).thenReturn(departmentEntities);
        when(departmentService.getDepartmentByStateAndCity(state, city)).thenReturn(departmentDtoList);
        when(utils.getDepartmentDtoList(departmentEntities)).thenReturn(departmentDtoList);

        List<DepartmentDto> departmentDtos = departmentService.getDepartmentByStateAndCity(state, city);

        assertThat(departmentDtos.get(0).getName()).isEqualTo(departmentDto1.getName());
        assertThat(departmentDtos.get(0).getCity()).isEqualTo(departmentDto1.getCity());
        assertThat(departmentDtos.get(0).getState()).isEqualTo(departmentDto1.getState());
        assertThat(departmentDtos.get(0).getCountry()).isEqualTo(departmentDto1.getCountry());
        assertThat(departmentDtos.get(0).getZipCode()).isEqualTo(departmentDto1.getZipCode());

    }

    @Test
    public void getDepartmentByCountry(){

        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");
        DepartmentDto departmentDto2 = new DepartmentDto(2, "name2","city2","state2","country2","zipcode2");
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        departmentDtoList.add(departmentDto);
        departmentDtoList.add(departmentDto2);

        DepartmentEntity departmentEntity = new DepartmentEntity(1, "name","city","state","country","zip code");
        DepartmentEntity departmentEntity1 = new DepartmentEntity(1, "name","city","state","country","zip code");
        Iterable<DepartmentEntity> departmentEntities = Arrays.asList(departmentEntity, departmentEntity1);

        String country = "country";
        when(departmentRepository.findByCountry(country)).thenReturn(departmentEntities);
        when(departmentService.getDepartmentByCountry(country)).thenReturn(departmentDtoList);
        when(utils.getDepartmentDtoList(departmentEntities)).thenReturn(departmentDtoList);

        List<DepartmentDto> departmentDtos = departmentService.getDepartmentByCountry(country);

        assertThat(departmentDtos.get(0).getName()).isEqualTo(departmentDto.getName());
        assertThat(departmentDtos.get(0).getCity()).isEqualTo(departmentDto.getCity());
        assertThat(departmentDtos.get(0).getState()).isEqualTo(departmentDto.getState());
        assertThat(departmentDtos.get(0).getCountry()).isEqualTo(departmentDto.getCountry());
        assertThat(departmentDtos.get(0).getZipCode()).isEqualTo(departmentDto.getZipCode());

    }

    @Test
    public void getDepartmentByZipCode(){

        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");
        DepartmentDto departmentDto2 = new DepartmentDto(2, "name2","city2","state2","country2","zipcode2");
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        departmentDtoList.add(departmentDto);
        departmentDtoList.add(departmentDto2);

        DepartmentEntity departmentEntity = new DepartmentEntity(1, "name","city","state","country","zip code");
        DepartmentEntity departmentEntity1 = new DepartmentEntity(1, "name","city","state","country","zip code");
        Iterable<DepartmentEntity> departmentEntities = Arrays.asList(departmentEntity, departmentEntity1);

        String zipCode = "zipcode";
        when(departmentRepository.findByZipCode(zipCode)).thenReturn(departmentEntities);
        when(departmentService.getDepartmentByZipCode(zipCode)).thenReturn(departmentDtoList);
        when(utils.getDepartmentDtoList(departmentEntities)).thenReturn(departmentDtoList);

        List<DepartmentDto> departmentDtos = departmentService.getDepartmentByZipCode(zipCode);

        assertThat(departmentDtos.get(0).getName()).isEqualTo(departmentDto.getName());
        assertThat(departmentDtos.get(0).getCity()).isEqualTo(departmentDto.getCity());
        assertThat(departmentDtos.get(0).getState()).isEqualTo(departmentDto.getState());
        assertThat(departmentDtos.get(0).getCountry()).isEqualTo(departmentDto.getCountry());
        assertThat(departmentDtos.get(0).getZipCode()).isEqualTo(departmentDto.getZipCode());

    }

    @Test
    public void createDepartment(){
        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");

        DepartmentDto departmentDtos = departmentService.createDepartment(departmentDto);

        assertThat(departmentDtos.getName()).isEqualTo(departmentDto.getName());
        assertThat(departmentDtos.getCity()).isEqualTo(departmentDto.getCity());
        assertThat(departmentDtos.getState()).isEqualTo(departmentDto.getState());
        assertThat(departmentDtos.getCountry()).isEqualTo(departmentDto.getCountry());
        assertThat(departmentDtos.getZipCode()).isEqualTo(departmentDto.getZipCode());
    }

    @Test
    public void deleteDepartmentNameTest(){
        String name = "name";
        doNothing().when(departmentRepository).deleteByName(name);
        boolean res = departmentService.deleteDepartmentByName(name);
        assertThat(res).isEqualTo(true);
    }

    @Test
    public void deleteDepartmentByNameExceptionTest() {
        String name = "name";
        doThrow(RuntimeException.class).when(departmentRepository).deleteByName(name);
        boolean res = departmentService.deleteDepartmentByName(name);
        assertThat(res).isEqualTo(false);
    }

    @Test
    public void getDepartmentByIdTest(){
        Optional<DepartmentEntity> departmentEntity = Optional.of(new DepartmentEntity(1, "name","city","state","country","zip code"));

        when(departmentRepository.findById(1L)).thenReturn(departmentEntity);

        DepartmentDto departmentDto1 = departmentService.getDepartmentById(1L);

        assertThat(departmentDto1.getName()).isEqualTo(departmentEntity.get().getName());
        assertThat(departmentDto1.getCity()).isEqualTo(departmentEntity.get().getCity());
        assertThat(departmentDto1.getState()).isEqualTo(departmentEntity.get().getState());
        assertThat(departmentDto1.getCountry()).isEqualTo(departmentEntity.get().getCountry());
        assertThat(departmentDto1.getZipCode()).isEqualTo(departmentEntity.get().getZipCode());

    }

}
