package in.rgukt.r081247.universityapi.repository;

import in.rgukt.r081247.universityapi.entity.Course;
import in.rgukt.r081247.universityapi.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    public Course findOneByDepartmentAndId(Department department, Long courseId);
}
