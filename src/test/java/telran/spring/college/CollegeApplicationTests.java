package telran.spring.college;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import telran.spring.college.entity.Lecturer;
import telran.spring.college.entity.Student;
import telran.spring.college.repo.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CollegeApplicationTests {
private static final long ID_LECTURER = 123;
private static final long ID_STUDENT = 124;
@Autowired
LecturerRepository lecturerRepository;
@Autowired
StudentRepository studentRepository;
Lecturer lecturer = new Lecturer(ID_LECTURER, "Vasya", LocalDate.now(), null, null);
Student student = new Student(ID_STUDENT, "Petya", LocalDate.now(), null, null);
	@Test
	void contextLoads() {
	}
	@Test
	@Order(1)
	void saveLectrurerStudentTest() {
		lecturerRepository.save(lecturer);
		studentRepository.save(student);
		
	}
	@Order(2)
	@Test
	void findLecturerStudentTest() {
		Lecturer lecturerRes = lecturerRepository.findById(ID_LECTURER).orElse(null);
		assertEquals(ID_LECTURER, lecturerRes.getId());
		Student studentRes = studentRepository.findById(ID_STUDENT).orElse(null);
		assertEquals(ID_STUDENT, studentRes.getId());
	}
	@Order(3)
	@Test
	void getAllStudents() {
		List objects = lecturerRepository.findAll();
		assertEquals(1, objects.size());
	}

}
