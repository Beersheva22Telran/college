package telran.spring.college;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import telran.spring.college.dto.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.spring.college.service.CollegeServcie;
@SpringBootTest
class ReadCollegeTest {
	@Autowired
CollegeServcie service;
	@Test
	@Sql(scripts = {"college-read-test-script.sql"})
	void bestStudentsLecturerTest() {
		List<IdName> actual = service.bestStudentsLecturer(321, 2);
		assertEquals(2, actual.size());
		assertEquals(127, actual.get(0).getId());
		assertEquals("Rivka", actual.get(0).getName());
		assertEquals(123, actual.get(1).getId());
		assertEquals("Vasya", actual.get(1).getName());
	}

}
