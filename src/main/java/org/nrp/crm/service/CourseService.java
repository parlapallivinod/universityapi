package org.nrp.crm.service;

import org.nrp.crm.exception.RecordNotFoundException;
import org.nrp.crm.entity.Course;
import org.nrp.crm.entity.Department;
import org.nrp.crm.repository.CourseRepository;
import org.nrp.crm.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.Optional;

@Service
@Transactional
public class CourseService {
	
	private static final RecordNotFoundException departmentNotFoundException = new RecordNotFoundException("Department not found");
	private static final RecordNotFoundException courseNotFoundException = new RecordNotFoundException("Course not found");


    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public Set<Course> getCoursesByDepartmentId(Long departmentId) {

        Optional<Department> department = departmentRepository.findById(departmentId);
        if (!department.isPresent())
            throw departmentNotFoundException;
        return department.get().getCourses();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public Course getCourseByDepartmentId(Long departmentId, Long courseId) {

        Optional<Department> department = departmentRepository.findById(departmentId);
        if (!department.isPresent())
            throw departmentNotFoundException;

        Course course = courseRepository.findOneByDepartmentAndId(department.get(), courseId);
        if (course == null)
            throw courseNotFoundException;
        return course;
    }

    @Secured({"ROLE_ADMIN"})
    public Course addCourseByDepartmentId(Long departmentId, Course course) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (!department.isPresent())
            throw departmentNotFoundException;
        course.setDepartment((department.get()));
        return courseRepository.save(course);
    }

    @Secured({"ROLE_ADMIN"})
    public Course deleteCourseByDeparmentId(Long departmentId, Long courseId) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (!department.isPresent())
            throw departmentNotFoundException;

        Course course = courseRepository.findOneByDepartmentAndId(department.get(), courseId);
        if (course == null)
            throw courseNotFoundException;

        courseRepository.delete(course);
        return course;
    }

    @Secured({"ROLE_ADMIN"})
    public Course updateCourseByDeparmentId(Long departmentId, Long courseId, Course course) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (!department.isPresent())
            throw departmentNotFoundException;

        Course object = courseRepository.findOneByDepartmentAndId(department.get(), courseId);
        if (object == null)
            throw courseNotFoundException;

        course.setId(courseId);
        course.setDepartment(department.get());
        return courseRepository.save(course);
    }

}
