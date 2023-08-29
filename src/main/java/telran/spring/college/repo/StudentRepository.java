package telran.spring.college.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.spring.college.dto.IdName;
import telran.spring.college.dto.StudentMark;
import telran.spring.college.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	String STUDENTS ="(select * from students_lecturers where dtype='Student') sl ";
	String ID_NAME_PROJECTION = "select sl.id as id, sl.name as name from ";
	String JOIN_MARKS = " join marks on sl.id=student_id ";
	String ID_NAME_MARK_PROJECTION = "select sl.id as id, sl.name as name, coalesce(round(avg(mark)), 0) as mark from ";
	String STUDENTS_MARKS = ID_NAME_PROJECTION + STUDENTS
			+ JOIN_MARKS;
	@Query(value= STUDENTS_MARKS +"join subjects sbj on subject_id=sbj.id where lecturer_id=:lecturerId "
			+ "group by sl.id order by avg(mark) desc limit :nStudents", nativeQuery = true)
List<IdName> findBestStudentsLecturer(long lecturerId, int nStudents);
	
	@Query(value= STUDENTS_MARKS + " group by sl.id having count(mark) >= :nMarks and "
					+ "avg(mark) >= (select avg(mark) from marks) order by avg(mark) desc", nativeQuery = true)
	List<IdName> findStudentsAvgMarkGreaterCollege(int nMarks);
	
	@Query(value= ID_NAME_MARK_PROJECTION + STUDENTS + "left " + JOIN_MARKS + " group by sl.id order by avg(mark) desc", nativeQuery = true)
	List<StudentMark> findStudentsMarks();

	@Query(value="select * from students_lecturers where dtype = 'Student' and"
			+ " id  in (select sl.id from students_lecturers sl left join marks on sl.id=student_id group by sl.id "
			+ "having count(mark) < :nMarks)", nativeQuery=true)
	List<Student> findStudentsLessMark(int nMarks);
	
}
