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

    @Test
    public void getDepartmentByNameExceptionTest() throws NotFoundException{
        DepartmentDto departmentDto1 = null;
        String name = null;

        when(departmentService.getDepartmentByName(name)).thenReturn(departmentDto1);
        ResponseEntity<DepartmentResponseModel> departmentResponseModelResponseEntity = departmentController.getDepartmentByName(name);

        DepartmentResponseModel departmentResponseModel1 = departmentResponseModelResponseEntity.getBody();
        assertThat(departmentResponseModelResponseEntity.getStatusCode().toString()).isEqualTo("404 NOT_FOUND");


    }

    @Test
    public void searchDepartmentTest() throws NotFoundException{
        DepartmentDto departmentDto1 = new DepartmentDto(1, "name","city","state","country","zip code");
        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code1");

        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        departmentDtoList.add(departmentDto);
        departmentDtoList.add(departmentDto1);

        String state = "State";
        String city = "City";

        List<DepartmentResponseModel> departmentList = new ArrayList<>();
        departmentDtoList.forEach(departmentDtoTemp -> {
            DepartmentResponseModel departmentResponseModel = new ModelMapper().map(departmentDtoTemp, DepartmentResponseModel.class);
            departmentList.add(departmentResponseModel);
        });
        when(departmentService.getDepartmentByStateAndCity(state, city)).thenReturn(departmentDtoList);
        when(utils.getDepartmentResponseModelList(departmentDtoList)).thenReturn(departmentList);

        ResponseEntity<List<DepartmentResponseModel>> responseEntity = departmentController.searchDepartments(state,city);


        List<DepartmentResponseModel> departmentResponseModelList = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().toString()).isEqualTo("200 OK");
        assert departmentResponseModelList != null;
        assertThat(departmentResponseModelList.get(0).getZipCode()).isEqualTo("zip code1");
        assertThat(departmentResponseModelList.get(1).getZipCode()).isEqualTo("zip code");

    }

    @Test
    public void searchDepartmentByCityTest() throws NotFoundException{
        DepartmentDto departmentDto1 = new DepartmentDto(1, "name","city","state","country","zip code");
        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");

        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        departmentDtoList.add(departmentDto);
        departmentDtoList.add(departmentDto1);

        String state = "State";
        String city = null;


        List<DepartmentResponseModel> departmentList = new ArrayList<>();
        departmentDtoList.forEach(departmentDtoTemp -> {
            DepartmentResponseModel departmentResponseModel = new ModelMapper().map(departmentDtoTemp, DepartmentResponseModel.class);
            departmentList.add(departmentResponseModel);
        });

        ResponseEntity<List<DepartmentResponseModel>> responseEntity = departmentController.searchDepartments(city,state);

        List<DepartmentResponseModel> departmentResponseModelList = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().toString()).isEqualTo("200 OK");

    }

    @Test
    public void searchDepartmentByStateTest() throws NotFoundException{
        DepartmentDto departmentDto1 = new DepartmentDto(1, "name","city","state","country","zip code");
        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");

        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        departmentDtoList.add(departmentDto);
        departmentDtoList.add(departmentDto1);

        String state = null;
        String city = "city";


        List<DepartmentResponseModel> departmentList = new ArrayList<>();
        departmentDtoList.forEach(departmentDtoTemp -> {
            DepartmentResponseModel departmentResponseModel = new ModelMapper().map(departmentDtoTemp, DepartmentResponseModel.class);
            departmentList.add(departmentResponseModel);
        });

        ResponseEntity<List<DepartmentResponseModel>> responseEntity = departmentController.searchDepartments(city,state);

        List<DepartmentResponseModel> departmentResponseModelList = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().toString()).isEqualTo("200 OK");

    }

    @Test
    public void createDepartmentTest() throws NotFoundException{
        DepartmentRequestModel departmentRequestModel = new DepartmentRequestModel("name","city","state","country","zip code");

        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");

        DepartmentResponseModel departmentResponseModel = new ModelMapper().map(departmentDto, DepartmentResponseModel.class);

        //we have to create new department, here we are checking if department exists or not
        when(utils.getDepartmentDto(departmentRequestModel)).thenReturn(departmentDto);
        when(departmentService.createDepartment(departmentDto)).thenReturn(departmentDto);
        when(utils.getDepartmentResponseModel(departmentDto)).thenReturn(departmentResponseModel);

        ResponseEntity<DepartmentResponseModel> responseEntity = departmentController.createDepartment(departmentRequestModel);

        DepartmentResponseModel departmentResponseModel1 = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().toString()).isEqualTo("201 CREATED");
        assert departmentResponseModel1 != null;
        assertThat(departmentResponseModel1.getName()).isEqualTo(departmentRequestModel.getName());

    }

    @Test
    public void updateDepartmentTest() throws NotFoundException{
        DepartmentRequestModel departmentRequestModel = new DepartmentRequestModel("name","city","state","country","zip code");

        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");

        DepartmentResponseModel departmentResponseModel = new ModelMapper().map(departmentDto, DepartmentResponseModel.class);

        //we have to update department, here we are checking if department exists or not
        when(departmentService.getDepartmentByName(departmentRequestModel.getName())).thenReturn(departmentDto);
        when(utils.getDepartmentDto(departmentRequestModel)).thenReturn(departmentDto);
        when(departmentService.createDepartment(departmentDto)).thenReturn(departmentDto);
        when(utils.getDepartmentResponseModel(departmentDto)).thenReturn(departmentResponseModel);

        ResponseEntity<DepartmentResponseModel> responseEntity = departmentController.updateDepartment(departmentRequestModel);
        DepartmentResponseModel departmentResponseModel1 = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().toString()).isEqualTo("200 OK");
        assert departmentResponseModel1 != null;
        assertThat(departmentResponseModel1.getName()).isEqualTo(departmentRequestModel.getName());

    }

    @Test
    public void deleteDepartmentTest() {
        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");
        String name = "name";

        when(departmentService.getDepartmentByName(name)).thenReturn(departmentDto);
        when(departmentService.deleteDepartmentByName(name)).thenReturn(true);
        ResponseEntity<Void> responseEntity = departmentController.delete(name);
        assertThat(responseEntity.getStatusCode().toString()).isEqualTo("204 NO_CONTENT");
    }

    @Test
    public void updateDepartmentExceptionTest() throws NotFoundException{
        DepartmentRequestModel departmentRequestModel = new DepartmentRequestModel("name","city","state","country","zip code");
        when(departmentService.getDepartmentByName(departmentRequestModel.getName())).thenReturn(null);

        try{
            departmentController.updateDepartment(departmentRequestModel);
        }catch (Exception ex){
            assertThat(ex.getMessage()).isEqualTo("Failed to update, user not found");
        }
    }

    @Test
    public void deleteDepartmentExceptionTest() {
        String name = "name";
        when(departmentService.getDepartmentByName(name)).thenReturn(null);
        try{
            departmentController.delete(name);
        }catch (Exception ex){
            assertThat(ex.getMessage()).isEqualTo("Failed to delete, department not found");
        }
    }

    @Test
    public void deleteDepartmentExceptionSecondTest() {
        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");
        String name = "name";
        when(departmentService.getDepartmentByName(name)).thenReturn(departmentDto);
        when(departmentService.deleteDepartmentByName(name)).thenReturn(false);
        try{
            departmentController.delete(name);
        }catch (Exception ex){
            assertThat(ex.getMessage()).isEqualTo("An error occurred while executing request");
        }
    }
}
