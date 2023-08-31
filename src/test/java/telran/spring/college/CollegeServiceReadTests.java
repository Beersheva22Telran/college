package telran.spring.college;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import telran.spring.college.dto.*;
import telran.spring.college.repo.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import telran.spring.college.entity.*;
import telran.spring.college.service.CollegeService;
@SpringBootTest
@Sql(scripts = {"college-read-test-script.sql"})
class CollegeServiceReadTests {
	@Autowired
CollegeService service;
	@Autowired
	SubjectRepository subjectRepo;
	@Autowired
	StudentRepository studentRepo;
	@Test
	
	void bestStudentsLecturerTest() {
		List<IdName> actual = service.bestStudentsLecturer(321, 2);
		assertEquals(2, actual.size());
		assertEquals(127, actual.get(0).getId());
		assertEquals("Rivka", actual.get(0).getName());
		assertEquals(123, actual.get(1).getId());
		assertEquals("Vasya", actual.get(1).getName());
	}
	
	@Test
	void bestStudentsAvgGreaterTest() {
		List<IdName> actual = service.studentsAvgMarksGreaterCollegeAvg(2);
		assertEquals(2, actual.size());
		assertEquals(127, actual.get(0).getId());
		assertEquals("Rivka", actual.get(0).getName());
		assertEquals(123, actual.get(1).getId());
		assertEquals("Vasya", actual.get(1).getName());
	}
	
	@Test
	void studentsMarks() {
		List<StudentMark> studentsMarks = service.studentsAvgMarks();
		assertEquals(5, studentsMarks.size());
		assertEquals(127, studentsMarks.get(0).getId());
		assertEquals("Rivka", studentsMarks.get(0).getName());
		assertEquals(100, studentsMarks.get(0).getMark());
		assertEquals(126, studentsMarks.get(4).getId());
		assertEquals("David", studentsMarks.get(4).getName());
		assertEquals(0, studentsMarks.get(4).getMark());
	}
	@Test
	void fetchLecturerNoTransactionTest() {
		Subject subject = subjectRepo.findById("S3").get();
		assertThrows(Exception.class, ()-> subject.getLecturer().getName());
		
	}
	
	@Test
	void fetchMarksNoTransactionTest() {
		Student student = studentRepo.findById(123l).get();
		assertThrows(Exception.class, ()-> student.getMarks().size());
		
	}
	@Test
	@Transactional(readOnly=true)
	void fetchLecturerTest() {
		Subject subject = subjectRepo.findById("S3").get();
		assertEquals("Sara Abramovna", subject.getLecturer().getName());
	}
	
	@Test
	@Transactional(readOnly=true)
	void fetchMarksTest() {
		Student student = studentRepo.findById(123l).get();
		assertEquals(4, student.getMarks().size());
	}
	@Test
	void jpqlSingleProjectionTest() {
		String query = "select id from Student order by id";
		List<String> res = service.jpqlQuery(query);
		assertEquals(5, res.size());
		String[] expected = {"123", "124", "125", "126", "127"};
		assertArrayEquals(expected, res.toArray(String[]::new));
		
	}
	@Test
	void jpqlMultyProjerctionTest() {
		String query = "select id, name from Student order by id";
		List<String> res = service.jpqlQuery(query);
		assertEquals(5, res.size());
		String[] expected = {"[123, Vasya]", "[124, Sara]", "[125, Yosef]", "[126, David]", "[127, Rivka]"};
		assertArrayEquals(expected, res.toArray(String[]::new));
	}
	
	

}
