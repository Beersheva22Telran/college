package telran.spring.college.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
	@Id
	String id;
	String name;
	int hours;
	@ManyToOne
	Lecturer lecturer;

}
