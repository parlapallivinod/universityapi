package in.rgukt.r081247.universityapi.service;

import in.rgukt.r081247.universityapi.exception.RecordNotFoundException;
import in.rgukt.r081247.universityapi.entity.Course;
import in.rgukt.r081247.universityapi.entity.Student;
import in.rgukt.r081247.universityapi.repository.CourseRepository;
import in.rgukt.r081247.universityapi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class StudentService {
	
	private static final RecordNotFoundException studentNotFoundException = new RecordNotFoundException("Student not found");
	private static final RecordNotFoundException courseNotFoundException = new RecordNotFoundException("Course not found");

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Secured({"ROLE_ADMIN"})
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public Student getStudentById(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (! student.isPresent())
            throw studentNotFoundException;

        return student.get();
    }

    @Secured({"ROLE_ADMIN"})
    public Student updateStudent(Long studentId, Student student) {

        Optional<Student> object = studentRepository.findById(studentId);
        if (!object.isPresent())
            throw studentNotFoundException;

        student.setId(studentId);
        return studentRepository.save(student);
    }

    @Secured({"ROLE_ADMIN"})
    public Student deleteStudentById(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (!student.isPresent())
            throw studentNotFoundException;

        studentRepository.delete(student.get());
        return student.get();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public Set<Course> getCoursesByStudentId(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (!student.isPresent())
            throw studentNotFoundException;

        return student.get().getCourses();
    }

    @Secured({"ROLE_ADMIN"})
    public Course addCourseByStudentId(Long studentId, Long courseId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (!student.isPresent())
            throw studentNotFoundException;

        Student studentObj = student.get();

        Optional<Course> course = courseRepository.findById(courseId);
        if (!course.isPresent())
            throw courseNotFoundException;
        Course courseObj = course.get();

        if (studentObj.getCourses().contains(courseObj))
            return courseObj;

        studentObj.getCourses().add(courseObj);
        studentRepository.save(studentObj);
        return courseObj;
    }

    @Secured({"ROLE_ADMIN"})
    public Course deleteCourseByStudentId(Long studentId, Long courseId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (!student.isPresent())
            throw studentNotFoundException;

        Student studentObj = student.get();

        Optional<Course> course = courseRepository.findById(courseId);
        if (!course.isPresent())
            throw courseNotFoundException;
        Course courseObj = course.get();

        if (! studentObj.getCourses().contains(courseObj))
            return null;

        studentObj.getCourses().remove(courseObj);
        studentRepository.save(studentObj);
        return courseObj;
    }
}
