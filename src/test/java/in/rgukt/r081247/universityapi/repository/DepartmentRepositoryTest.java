package in.rgukt.r081247.universityapi.repository;

import in.rgukt.r081247.universityapi.entity.Department;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.Optional;

import org.assertj.core.api.Assertions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class DepartmentRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Test
	public void testAddDepartment1() {
		Department department = new Department();
		department.setName("Computer Science");

		Department savedInDb = entityManager.persist(department);
		Optional<Department> temp = departmentRepository.findById(savedInDb.getId());
		Department getFromDb = null;
		if (temp.isPresent()) {
			getFromDb = temp.get();
		}

		Assertions.assertThat(getFromDb).isEqualTo(savedInDb);
		System.out.println("DepartmentRepositoryTest.testAddDepartment1()");
	}

	@Test
	public void testAddDepartment2(){
		Department department = new Department();
		//department.setName("Computer Science");

		Exception exception = assertThrows(Exception.class, () -> {
			Department savedInDb = entityManager.persist(department);
		});
		System.out.println("Exception: " + exception);
		assertThat(exception).isNotNull();
		System.out.println("DepartmentRepositoryTest.testAddDepartment2()");
	}

	@Test
	public void testGetDepartmentById1() {
		Department department = new Department();
		department.setName("Physics");

		Department savedInDb = entityManager.persist(department);
		Optional<Department> temp = departmentRepository.findById(savedInDb.getId());
		Department getFromDb = null;
		if (temp.isPresent()) {
			getFromDb = temp.get();
		}

		Assertions.assertThat(getFromDb).isEqualTo(savedInDb);
		System.out.println("DepartmentRepositoryTest.testGetDepartmentById1()");
	}
}
