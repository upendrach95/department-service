package com.departmentservice.department;

import com.departmentservice.department.Controller.DepartmentController;
import com.departmentservice.department.exception.NotFoundException;
import com.departmentservice.department.model.DepartmentRequestModel;
import com.departmentservice.department.model.DepartmentResponseModel;
import com.departmentservice.department.service.DepartmentService;
import com.departmentservice.department.shared.DepartmentDto;
import com.departmentservice.department.shared.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpServerErrorException;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    Utils utils;

    @MockBean
    DepartmentService departmentService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void getDepartmentByIdTest() throws Exception{
        DepartmentDto departmentDto1 = new DepartmentDto(1, "name","city","state","country","zip code");

        DepartmentResponseModel departmentResponseModel = new ModelMapper().map(departmentDto1, DepartmentResponseModel.class);

        when(departmentService.getDepartmentById(1)).thenReturn(departmentDto1);
        when(utils.getDepartmentResponseModel(departmentDto1)).thenReturn(departmentResponseModel);


        mockMvc.perform(get("/department/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(departmentDto1.getName())));
    }

    @Test
    public void getDepartmentByIdNotFoundTest() throws Exception{

        when(departmentService.getDepartmentById(1)).thenReturn(null);
        when(utils.getDepartmentResponseModel(null)).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/department/id/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status", is("Failed")));
    }

    @Test
    public void createDepartmentTest() throws Exception{
        DepartmentRequestModel departmentRequestModel = new DepartmentRequestModel("name","city","state","country","zip code");

        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");

        when(departmentService.getDepartmentByName(departmentRequestModel.getName())).thenReturn(null);

        Utils utils1 = Mockito.mock(Utils.class, Mockito.withSettings().defaultAnswer(InvocationOnMock::callRealMethod));
        when(utils1.getDepartmentDto(departmentRequestModel)).thenReturn(departmentDto);

        when(departmentService.createDepartment(departmentDto)).thenReturn(departmentDto);

        mockMvc.perform(post("/department")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(departmentRequestModel)))
                .andExpect(status().isCreated());

    }

    @Test
    public void createDepartmentExceptionTest() throws Exception{
        DepartmentRequestModel departmentRequestModel = new DepartmentRequestModel("name","city","state","country","zip code");

        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");


        when(departmentService.getDepartmentByName(departmentRequestModel.getName())).thenReturn(departmentDto);

        mockMvc.perform(post("/department")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(departmentRequestModel)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status", is("Failed")))
                .andExpect(jsonPath("message", is("Provided name already exists")));
    }

    /*@Test
    public void createDepartmentFieldExceptionTest() throws Exception{
        DepartmentRequestModel departmentRequestModel = new DepartmentRequestModel("name","city","state","country","zip code");

        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");


        when(departmentService.getDepartmentByName(departmentRequestModel.getName())).thenReturn(departmentDto);

        mockMvc.perform(post("/department")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(departmentRequestModel)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status", is("Failed")))
                .andExpect(jsonPath("$[0].message", is("Name cannot be less than 2 characters")));


    }*/

   @Test
    public void createDepartmentPathExceptionTest() throws Exception{
        DepartmentRequestModel departmentRequestModel = new DepartmentRequestModel("name","city","state","country","zip code");

        DepartmentDto departmentDto = new DepartmentDto(1, "name","city","state","country","zip code");


        when(departmentService.getDepartmentByName(departmentRequestModel.getName())).thenReturn(departmentDto);

        mockMvc.perform(delete("/department")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(departmentRequestModel)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status", is("Failed")))
                .andExpect(jsonPath("message", is("Wrong http method or routing")));
    }

    @Test
    public void deleteDepartmentGeneralExceptionTest() throws Exception{
        when(departmentService.deleteDepartmentByName("name")).thenReturn(false);
        doThrow(HttpServerErrorException.InternalServerError.class).when(departmentService).getDepartmentByName("name");
        mockMvc.perform(delete("/department/name"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status", is("Failed")))
                .andExpect(jsonPath("message", is("An exception occurred while processing request")));
    }

}
