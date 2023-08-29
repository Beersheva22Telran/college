package telran.spring.college.entity;

import java.util.List;

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
	@OneToMany(mappedBy="student", cascade = CascadeType.REMOVE)
	List<Mark> marks;

}
