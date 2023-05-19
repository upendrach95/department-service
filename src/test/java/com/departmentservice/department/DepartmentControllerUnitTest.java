package com.departmentservice.department;

import com.departmentservice.department.Controller.DepartmentController;
import com.departmentservice.department.exception.NotFoundException;
import com.departmentservice.department.model.DepartmentRequestModel;
import com.departmentservice.department.model.DepartmentResponseModel;
import com.departmentservice.department.service.DepartmentServiceImpl;
import com.departmentservice.department.shared.DepartmentDto;
import com.departmentservice.department.shared.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentControllerUnitTest {

    @InjectMocks
    DepartmentController departmentController;

    @Mock
    Utils utils;

    @Mock
    DepartmentServiceImpl departmentService;


    @Test
    public void getAllDepartmentsTest() throws NotFoundException {
        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");
        DepartmentDto departmentDto2 = new DepartmentDto(2, "name2","city2","state2","country2","zipcode2");
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        departmentDtoList.add(departmentDto);
        departmentDtoList.add(departmentDto2);

        List<DepartmentResponseModel> departmentsList = new ArrayList<>();
        departmentDtoList.forEach(departmentDtoTemp -> {
            DepartmentResponseModel departmentResponseModel = new ModelMapper().map(departmentDtoTemp, DepartmentResponseModel.class);
            departmentsList.add(departmentResponseModel);
        });

        when(departmentService.getAllDepartments()).thenReturn(departmentDtoList);
        when(utils.getDepartmentResponseModelList(departmentDtoList)).thenReturn(departmentsList);

        ResponseEntity<List<DepartmentResponseModel>> responseEntity = departmentController.getAllDepartments();
        List<DepartmentResponseModel> departmentResponseModelList = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().toString()).isEqualTo("200 OK");
    }

    @Test
    public void getDepartmentByIdTest() throws NotFoundException {
        DepartmentDto departmentDto1 = new DepartmentDto(1, "name","city","state","country","zip code");

        DepartmentResponseModel departmentResponseModel = new ModelMapper().map(departmentDto1, DepartmentResponseModel.class);

        when(departmentService.getDepartmentById(1)).thenReturn(departmentDto1);
        when(utils.getDepartmentResponseModel(departmentDto1)).thenReturn(departmentResponseModel);

        ResponseEntity<DepartmentResponseModel> departmentResponseModelResponseEntity = departmentController.getDepartmentById(1L);

        DepartmentResponseModel departmentResponseModel1 = departmentResponseModelResponseEntity.getBody();
        assertThat(departmentResponseModelResponseEntity.getStatusCode().toString()).isEqualTo("200 OK");
        assert departmentResponseModel1 != null;
        assertThat(departmentResponseModel1.getName()).isEqualTo(departmentDto1.getName());

    }

    @Test
    public void getDepartmentByNameTest() throws NotFoundException{
        DepartmentDto departmentDto1 = new DepartmentDto(1, "name","city","state","country","zip code");

        DepartmentResponseModel departmentResponseModel = new ModelMapper().map(departmentDto1, DepartmentResponseModel.class);
        String name = "name";

        when(departmentService.getDepartmentByName(name)).thenReturn(departmentDto1);
        when(utils.getDepartmentResponseModel(departmentDto1)).thenReturn(departmentResponseModel);

        ResponseEntity<DepartmentResponseModel> departmentResponseModelResponseEntity = departmentController.getDepartmentByName(name);

        DepartmentResponseModel departmentResponseModel1 = departmentResponseModelResponseEntity.getBody();
        assertThat(departmentResponseModelResponseEntity.getStatusCode().toString()).isEqualTo("200 OK");
        assert departmentResponseModel1 != null;
        assertThat(departmentResponseModel1.getName()).isEqualTo(name);

    }
}
