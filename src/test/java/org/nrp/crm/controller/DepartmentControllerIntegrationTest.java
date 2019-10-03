package org.nrp.crm.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nrp.crm.CrmApplication;
import org.nrp.crm.entity.Department;;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Test
	//@Ignore
	public void testAddDepartment() throws Exception {

		Department department = new Department();
		department.setId(1L);
		department.setName("Mathematics");
		
		String URIToCreateDepartment= "/api/departments";
		
	    String inputInJson = this.mapToJson(department);
		HttpHeaders headers = new HttpHeaders();
	    headers.setBasicAuth("admin", "adminpass");
		HttpEntity<Department> entity = new HttpEntity<>(department, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				formFullURLWithPort(URIToCreateDepartment),
				HttpMethod.POST, entity, String.class);
		
		String responseInJson = response.getBody();
		System.out.println("responseIn: " + responseInJson);
		System.out.println("inputIn: " + inputInJson);
		assertThat(responseInJson).isEqualTo(inputInJson);
	}
	

	@Test
	public void testGetDepartmentById() throws Exception {
		
		Department department = new Department();
		department.setId(2L);
		department.setName("Test Dept");

		String inputInJson = this.mapToJson(department);
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("admin", "adminpass");
		String URIToCreateDepartment = "/api/departments";
		HttpEntity<Department> entity = new HttpEntity<Department>(department, headers);
		testRestTemplate.exchange(formFullURLWithPort(URIToCreateDepartment),
				HttpMethod.POST, entity, String.class);

		HttpEntity<Object> emptyEntity = new HttpEntity<>(null, headers);
		String URI = "/api/departments/2";

		ResponseEntity<String> response= testRestTemplate.exchange(formFullURLWithPort(URI), HttpMethod.GET, emptyEntity, String.class);
		System.out.println(inputInJson);
		System.out.println(response.getBody());
		assertThat(response.getBody()).isEqualTo(inputInJson);
	}

	/*
	@Test
	public void testGetTicketByEmail() throws Exception {
		
		Ticket ticket = new Ticket();
		ticket.setTicketId(3);
		ticket.setPassengerName("Marry Johnson");
		ticket.setSourceStation("Delhi");
		ticket.setDestStation("Mumbai");
		ticket.setBookingDate(new Date());
		ticket.setEmail("marrry.j2017@gmail.com");
		
		String URI = "/api/tickets/create";
		
	    String inputInJson = this.mapToJson(ticket);
		
		HttpEntity<Ticket> entity = new HttpEntity<Ticket>(ticket, headers);
		testRestTemplate.exchange(
				formFullURLWithPort(URI),
				HttpMethod.POST, entity, String.class);
		
		String URIToGetTicket = "/api/tickets/email/marrry.j2017@gmail.com";
		String bodyJsonResponse = testRestTemplate.getForObject(formFullURLWithPort(URIToGetTicket), String.class);
		assertThat(bodyJsonResponse).isEqualTo(inputInJson);
		
	}
	*/
	
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