package org.nrp.crm.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.security.access.AccessDeniedException;

import org.nrp.crm.repository.DepartmentRepository;
import org.nrp.crm.entity.Department;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentServiceTest {

	@Autowired
	private DepartmentService departmentService;
	
	@MockBean
	private DepartmentRepository departmentRepository;

	@Test(expected = AccessDeniedException.class)
	@WithMockUser(username = "user", roles = {"USER"})
	public void testAddDepartment1(){

		Department department = new Department();
		department.setId(1L);
		department.setName("Mathematics");

		Mockito.when(departmentRepository.save(department)).thenReturn(department);
		assertThat(departmentService.addDepartment(department)).isEqualTo(department);
	}

	@WithMockUser(username = "admin", roles = {"ADMIN"})
	@Test
	public void testAddDepartment2(){

		Department department = new Department();
		department.setId(1L);
		department.setName("Mathematics");

		Mockito.when(departmentRepository.save(department)).thenReturn(department);
		assertThat(departmentService.addDepartment(department)).isEqualTo(department);
	}

	@Test(expected = ConstraintViolationException.class)
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	public void testAddDepartment3(){

		Department department = new Department();
		department.setId(1L);

		Mockito.when(departmentRepository.save(department)).thenThrow(ConstraintViolationException.class);
		assertThat(departmentService.addDepartment(department)).isEqualTo(department);
	}

	@Test(expected = AccessDeniedException.class)
	@WithMockUser(username = "test", roles = {"TEST"})
	public void testGetDepartmentById1(){
		Department department = new Department();
		department.setId(1L);
		department.setName("Physics");

		Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
		assertThat(departmentService.getDepartmentById(1L)).isEqualTo(department);
	}

	@Test
	@WithMockUser(username = "user", roles = {"USER"})
	public void testGetDepartmentById2(){
		Department department = new Department();
		department.setId(1L);
		department.setName("Physics");

		Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
		assertThat(departmentService.getDepartmentById(1L)).isEqualTo(department);
	}

}