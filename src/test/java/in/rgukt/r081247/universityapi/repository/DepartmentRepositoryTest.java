package in.rgukt.r081247.universityapi.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import in.rgukt.r081247.universityapi.CrmApplication;
import in.rgukt.r081247.universityapi.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import org.assertj.core.api.Assertions;


@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {CrmApplication.class})
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
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddDepartment2(){
		Department department = new Department();
		//department.setName("Computer Science");

		Department savedInDb = entityManager.persist(department);
		Optional<Department> temp = departmentRepository.findById(savedInDb.getId());
		Department getFromDb = null;
		if (temp.isPresent()) {
			getFromDb = temp.get();
		}

		Assertions.assertThat(getFromDb).isEqualTo(savedInDb);

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
	}
}
