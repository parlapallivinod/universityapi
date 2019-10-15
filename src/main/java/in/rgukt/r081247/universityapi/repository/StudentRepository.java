package in.rgukt.r081247.universityapi.repository;

import in.rgukt.r081247.universityapi.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
