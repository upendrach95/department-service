package com.departmentservice.department;

import com.departmentservice.department.data.DepartmentEntity;
import com.departmentservice.department.exception.NotFoundException;
import com.departmentservice.department.model.DepartmentRequestModel;
import com.departmentservice.department.model.DepartmentResponseModel;
import com.departmentservice.department.shared.DepartmentDto;
import com.departmentservice.department.shared.Utils;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UtilsTest {
    @Autowired
    Utils utils;

    @Test
    public void getDepartmentResponseModelListTest() throws NotFoundException {
        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");
        DepartmentDto departmentDto2 = new DepartmentDto(2, "name2","city2","state2","country2","zipcode2");
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        departmentDtoList.add(departmentDto);
        departmentDtoList.add(departmentDto2);

        List<DepartmentResponseModel> departmentList = utils.getDepartmentResponseModelList(departmentDtoList);

        assertThat(departmentDtoList.get(0).getName()).isEqualTo(departmentList.get(0).getName());
        assertThat(departmentDtoList.get(0).getCity()).isEqualTo(departmentList.get(0).getCity());
        assertThat(departmentDtoList.get(0).getState()).isEqualTo(departmentList.get(0).getState());
        assertThat(departmentDtoList.get(0).getCountry()).isEqualTo(departmentList.get(0).getCountry());
        assertThat(departmentDtoList.get(0).getZipCode()).isEqualTo(departmentList.get(0).getZipCode());

    }

    @Test
    public void getDepartmentResponseModelListNotFoundExceptionTest(){
        List<DepartmentDto> list = null;

        assertThatThrownBy(() -> utils.getDepartmentResponseModelList(list))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("0 records found");
    }

    @Test
    public void getDepartmentResponseModelListNotFoundExceptionTest2(){
        List<DepartmentDto> list = new ArrayList<>();

        assertThatThrownBy(() -> utils.getDepartmentResponseModelList(list))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("0 records found");
    }

    @Test
    public void getDepartmentResponseModelTest() throws NotFoundException {
        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");

        DepartmentResponseModel departmentResponseModel = utils.getDepartmentResponseModel(departmentDto);

        assertThat(departmentDto.getName()).isEqualTo(departmentResponseModel.getName());
        assertThat(departmentDto.getCity()).isEqualTo(departmentResponseModel.getCity());
        assertThat(departmentDto.getState()).isEqualTo(departmentResponseModel.getState());
        assertThat(departmentDto.getCountry()).isEqualTo(departmentResponseModel.getCountry());
        assertThat(departmentDto.getZipCode()).isEqualTo(departmentResponseModel.getZipCode());
    }

    @Test
    public void getDepartmentResponseModelNotFoundExceptionTest(){
        DepartmentDto departmentDto = null;
        assertThatThrownBy(() -> utils.getDepartmentResponseModel(departmentDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Department not found");

    }

    @Test
    public void getDepartmentDtoNotFoundExceptionTest(){
        DepartmentRequestModel departmentDetails = null;

        assertThatThrownBy(() -> utils.getDepartmentDto(departmentDetails))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Request object is null");
    }

    @Test
    public void getDepartmentDtoListTest(){
        DepartmentEntity departmentEntity = new DepartmentEntity(1, "name","city","state","country","zip code");

        DepartmentEntity departmentEntity1 = new DepartmentEntity(1, "name","city","state","country","zip code");

        Iterable<DepartmentEntity> departmentEntities = Arrays.asList(departmentEntity, departmentEntity1);

        List<DepartmentDto> allDepartments = utils.getDepartmentDtoList(departmentEntities);

        assertThat(allDepartments.get(0).getName()).isEqualTo(departmentEntity.getName());
        assertThat(allDepartments.get(0).getCity()).isEqualTo(departmentEntity.getCity());
        assertThat(allDepartments.get(0).getState()).isEqualTo(departmentEntity.getState());
        assertThat(allDepartments.get(0).getCountry()).isEqualTo(departmentEntity.getCountry());
        assertThat(allDepartments.get(0).getZipCode()).isEqualTo(departmentEntity.getZipCode());

    }
}
