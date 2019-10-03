package org.nrp.crm.repository;

import org.nrp.crm.entity.Course;
import org.nrp.crm.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    public Course findOneByDepartmentAndId(Department department, Long courseId);
}
