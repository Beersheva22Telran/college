package telran.spring.college.entity;
import java.time.LocalDate;

import jakarta.persistence.*;
import telran.spring.college.dto.PersonDto;

@Entity


public class Student extends Person{

	public Student() {
		
	}

	private Student(PersonDto person) {
		super(person);
		
	}
	public static Student of(PersonDto person) {
		return new Student(person);
	}

}
