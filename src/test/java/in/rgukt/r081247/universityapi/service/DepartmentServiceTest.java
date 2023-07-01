package in.rgukt.r081247.universityapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;;
import org.springframework.security.access.AccessDeniedException;

import in.rgukt.r081247.universityapi.repository.DepartmentRepository;
import in.rgukt.r081247.universityapi.entity.Department;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@SpringBootTest
public class DepartmentServiceTest {

	@Autowired
	private DepartmentService departmentService;
	
	@MockBean
	private DepartmentRepository departmentRepository;

	@Test
	@WithMockUser(username = "user", roles = {"USER"})
	public void testAddDepartment1(){

		Department department = new Department();
		department.setId(1L);
		department.setName("Mathematics");

		Mockito.when(departmentRepository.save(department)).thenReturn(department);
		AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> {
			assertThat(departmentService.addDepartment(department)).isEqualTo(department);
		});
		System.out.println("Exception: " + exception);
		assertThat(exception).isNotNull();
		System.out.println("DepartmentServiceTest.testAddDepartment1()");
	}

	@WithMockUser(username = "admin", roles = {"ADMIN"})
	@Test
	public void testAddDepartment2(){

		Department department = new Department();
		department.setId(1L);
		department.setName("Mathematics");

		Mockito.when(departmentRepository.save(department)).thenReturn(department);
		assertThat(departmentService.addDepartment(department)).isEqualTo(department);
		System.out.println("DepartmentServiceTest.testAddDepartment2()");
	}

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	public void testAddDepartment3(){

		Department department = new Department();
		department.setId(1L);

		Mockito.when(departmentRepository.save(department)).thenThrow(ConstraintViolationException.class);
		ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
			assertThat(departmentService.addDepartment(department)).isEqualTo(department);
		});
		System.out.println("Exception: " + exception);
		assertThat(exception).isNotNull();
		System.out.println("DepartmentServiceTest.testAddDepartment3()");
	}

	@Test
	@WithMockUser(username = "test", roles = {"TEST"})
	public void testGetDepartmentById1(){
		Department department = new Department();
		department.setId(1L);
		department.setName("Physics");

		Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
		AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> {
			assertThat(departmentService.getDepartmentById(1L)).isEqualTo(department);
		});
		System.out.println("Exception: " + exception);
		assertThat(exception).isNotNull();
		System.out.println("DepartmentServiceTest.testGetDepartmentById1()");
	}

	@Test
	@WithMockUser(username = "user", roles = {"USER"})
	public void testGetDepartmentById2(){
		Department department = new Department();
		department.setId(1L);
		department.setName("Physics");

		Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
		assertThat(departmentService.getDepartmentById(1L)).isEqualTo(department);
		System.out.println("DepartmentServiceTest.testGetDepartmentById2()");
	}
}