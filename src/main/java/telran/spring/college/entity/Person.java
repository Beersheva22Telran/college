package telran.spring.college.entity;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="students_lecturers")
abstract public class Person {
	@Id
	long id;
	String name;
	@Column(name = "birth_date")
	@Temporal(TemporalType.DATE)
	LocalDate birthDate;
	@Column(nullable = true)
	String city;
	@Column(nullable = true)
	String phone;
	
}
