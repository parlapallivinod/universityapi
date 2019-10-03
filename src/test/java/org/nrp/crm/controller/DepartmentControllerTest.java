package org.nrp.crm.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nrp.crm.entity.Department;
import org.nrp.crm.service.DepartmentService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=DepartmentController.class)
public class DepartmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DepartmentService departmentService;
	
	@Test
	@WithMockUser(username="admin")
	//@Ignore
	public void testAddDepartment() throws Exception {
		
		Department mockDepartment = new Department();
		mockDepartment.setId(1L);
		mockDepartment.setName("Mathematics");
		
		String inputInJson = this.mapToJson(mockDepartment);
		
		String URI = "/api/departments";
		
		Mockito.when(departmentService.addDepartment(Mockito.any(Department.class))).thenReturn(mockDepartment);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		String outputInJson = response.getContentAsString();
		
		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@Test
	@WithMockUser(username="abc", roles = {"ABC"})
	public void testGetDepartmentById() throws Exception {
		Department mockDepartment = new Department();
		mockDepartment.setId(1L);
		mockDepartment.setName("Physics");

		Mockito.when(departmentService.getDepartmentById(mockDepartment.getId())).thenReturn(mockDepartment);

		String URI = "/api/departments/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				URI).accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(mockDepartment);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	/**
	 * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
	 */
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
}