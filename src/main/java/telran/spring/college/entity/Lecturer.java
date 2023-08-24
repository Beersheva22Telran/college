package telran.spring.college.entity;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Entity

@NoArgsConstructor
public class Lecturer extends Person {

	public Lecturer(long id, String name, LocalDate birthDate, String city, String phone) {
		super(id, name, birthDate, city, phone);
		
	}
	
	
}
