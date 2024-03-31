package in.rgukt.r081247.universityapi.controller;

import in.rgukt.r081247.universityapi.entity.Course;
import in.rgukt.r081247.universityapi.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/departments/{departmentId}/courses")
public class CourseController {
	@Autowired
	private CourseService courseService;

	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getCoursesByDepartmentId(@PathVariable("departmentId") Long departmentId) {
		Set<Course> courses = courseService.getCoursesByDepartmentId(departmentId);
		return new ResponseEntity<>(courses, HttpStatus.OK);
	}

	@GetMapping(path = "/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getCourseByDepartmentId(@PathVariable("departmentId") Long departmentId,
													 @PathVariable("courseId") Long courseId) {
		Course course = courseService.getCourseByDepartmentId(departmentId, courseId);
		return new ResponseEntity<>(course, HttpStatus.OK);
	}


	@PutMapping(path = "/{courseId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateCourseByDepartmentId(@PathVariable("departmentId") Long departmentId,
															 @PathVariable("courseId") Long courseId,
															 @Valid @RequestBody Course course) {
		Course object = courseService.updateCourseByDeparmentId(departmentId, courseId, course);
		return new ResponseEntity<>((Course) object, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteCourseByDepartmentId(@PathVariable("departmentId") Long departmentId,
														@PathVariable("courseId") Long courseId) {
		courseService.deleteCourseByDeparmentId(departmentId, courseId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addCourse(@PathVariable("departmentId") Long departmentId,
									   @Valid @RequestBody Course course) {
		Course object = courseService.addCourseByDepartmentId(departmentId, course);
		return new ResponseEntity<>(object, HttpStatus.CREATED);
	}
}
