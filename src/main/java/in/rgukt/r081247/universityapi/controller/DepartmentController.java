package in.rgukt.r081247.universityapi.controller;

import in.rgukt.r081247.universityapi.entity.Department;
import in.rgukt.r081247.universityapi.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/departments")
public class DepartmentController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	private DepartmentService departmentService;

	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getDepartments() {
		LOGGER.info("Department Get ALL");
		List<Department> departments = departmentService.getDepartments();
		return new ResponseEntity<>(departments, HttpStatus.OK);
	}

	@GetMapping(path = "/{departmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getDepartment(@PathVariable("departmentId") Long departmentId) {
		Department department = departmentService.getDepartmentById(departmentId);
		return new ResponseEntity<>(department, HttpStatus.OK);

	}

	@PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addDepartment(@Valid @RequestBody Department department) {
		Department object = departmentService.addDepartment(department);
		return new ResponseEntity<>(object, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{departmentId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateDepartment(@PathVariable("departmentId") Long departmentId,
													   @Valid @RequestBody Department department) {
		Department object = departmentService.updateDepartment(departmentId, department);
		return new ResponseEntity<>(object, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{departmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteDepartment(@PathVariable("departmentId") Long departmentId) {
		departmentService.deleteDepartmentById(departmentId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
