package com.departmentservice.department;

import com.departmentservice.department.Controller.Controller;
import com.departmentservice.department.Controller.DepartmentController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DepartmentApplicationTests {

	@Autowired
	Controller controller;

	@Autowired
	DepartmentController departmentController;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(controller);
		Assertions.assertNotNull(departmentController);

	}
	@Test
	public void applicationContextTest(){
		DepartmentApplication.main(new String[]{});
	}

	@Test
	public void controllerTest(){
		assertThat(controller.checkingDepartmentService()).isEqualTo("The department service is working");
	}

}
