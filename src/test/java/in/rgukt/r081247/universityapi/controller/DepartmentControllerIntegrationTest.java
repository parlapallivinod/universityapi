package in.rgukt.r081247.universityapi.controller;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.rgukt.r081247.universityapi.CrmApplication;
import in.rgukt.r081247.universityapi.entity.Department;
import org.springframework.test.annotation.Rollback;;

@SpringBootTest(classes = CrmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class DepartmentControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Test
	@Rollback
	public void testAddDepartment() throws Exception {

		Department department = new Department();
		//department.setId(1L);
		department.setName("Mathematics");
		
		String URIToCreateDepartment= "/api/departments";
		
	    String inputInJson = this.mapToJson(department);
		HttpHeaders headers = new HttpHeaders();
	    headers.setBasicAuth("admin", "adminpass");
		HttpEntity<Department> entity = new HttpEntity<>(department, headers);
		ResponseEntity<Department> response = testRestTemplate.exchange(
				formFullURLWithPort(URIToCreateDepartment),
				HttpMethod.POST, entity, Department.class);
		
		Department responseDepartment = response.getBody();
		System.out.println("response: " + responseDepartment);
		System.out.println("input: " + department);
		assertThat(department.getName()).isEqualTo(responseDepartment.getName());
		System.out.println("DepartmentControllerIntegrationTest.testAddDepartment()");
	}

	@Test
	public void testGetDepartmentById() throws Exception {
		
		Department department = new Department();
		//department.setId(2L);
		department.setName("Test Dept");

		String inputInJson = this.mapToJson(department);
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("admin", "adminpass");
		String URIToCreateDepartment = "/api/departments";
		HttpEntity<Department> entity = new HttpEntity<Department>(department, headers);
		ResponseEntity<Department> responseDepartment = testRestTemplate.exchange(formFullURLWithPort(URIToCreateDepartment),
				HttpMethod.POST, entity, Department.class);

		HttpEntity<Object> emptyEntity = new HttpEntity<>(null, headers);
		String URI = "/api/departments/" + responseDepartment.getBody().getId().toString();

		ResponseEntity<Department> response= testRestTemplate.exchange(formFullURLWithPort(URI), HttpMethod.GET, emptyEntity, Department.class);
		System.out.println(inputInJson);
		System.out.println(response.getBody());
		assertThat(response.getBody().getName()).isEqualTo(department.getName());
		System.out.println("DepartmentControllerIntegrationTest.testGetDepartmentById()");
	}

	/**
	 * this utility method Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
	 */
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

    /**
     * This utility method to create the url for given uri. It appends the RANDOM_PORT generated port
     */
	private String formFullURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}