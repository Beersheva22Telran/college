package telran.spring.college.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.spring.college.dto.IdName;
import telran.spring.college.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	@Query(value="select sl.id as id, sl.name as name from students_lecturers sl  join marks on sl.id=student_id and  sl.dtype = 'Student' join subjects sbj on subject_id=sbj.id where lecturer_id=:lecturerId "
			+ "group by sl.id order by avg(mark) desc limit :nStudents", nativeQuery = true)
List<IdName> findBestStudentsLecturer(long lecturerId, int nStudents);
}
