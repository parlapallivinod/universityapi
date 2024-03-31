package in.rgukt.r081247.universityapi.controller;

import in.rgukt.r081247.universityapi.entity.Course;
import in.rgukt.r081247.universityapi.entity.Student;
import in.rgukt.r081247.universityapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/students")
public class StudentController {
	@Autowired
	private StudentService studentService;

	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getStudents() {
		List<Student> students = studentService.getStudents();
		return new ResponseEntity<>(students, HttpStatus.OK);
	}

	@GetMapping(path = "/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getStudent(@PathVariable("studentId") Long studentId) {
		Student student = studentService.getStudentById(studentId);
		return new ResponseEntity<>(student, HttpStatus.OK);

	}

	@PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addStudent(@Valid @RequestBody Student student) {
		Student object = studentService.addStudent(student);
		return new ResponseEntity<>(object, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{studentId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateStudent(@PathVariable("studentId") Long studentId,
										   @Valid @RequestBody Student student) {
		Student object = studentService.updateStudent(studentId, student);
		return new ResponseEntity<>(object, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteStudent(@PathVariable("studentId") Long studentId) {
		studentService.deleteStudentById(studentId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/{studentId}/courses", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getCoursesByStudentId(@PathVariable("studentId") Long studentId) {
		Set<Course> courses = studentService.getCoursesByStudentId(studentId);
		return new ResponseEntity<>((Set<Course>) courses, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{studentId}/courses/{courseId}")
	public ResponseEntity<Object> deleteCoursesByStudentId(@PathVariable("studentId") Long studentId,
														   @PathVariable("courseId") Long courseId) {
		studentService.deleteCourseByStudentId(studentId, courseId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping(path = "/{studentId}/courses/{courseId}",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addCourseByStudentId(@PathVariable("studentId") Long studentId,
												   @PathVariable("courseId") Long courseId) {
		Course course = studentService.addCourseByStudentId(studentId, courseId);
		return new ResponseEntity<>(course, HttpStatus.OK);
	}


}
