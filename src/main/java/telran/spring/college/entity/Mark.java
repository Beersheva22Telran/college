package telran.spring.college.entity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import telran.spring.college.dto.MarkDto;
@Entity
@NoArgsConstructor
@Table(name="marks", indexes = {@Index(columnList = "student_id")})
public class Mark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="student_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	Student student;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="subject_id", nullable = false)
	Subject subject;
	@Column(nullable=false)
	int mark;
	public Mark(Student student, Subject subject, int mark) {
		this.student = student;
		this.subject = subject;
		this.mark = mark;
		
	}
	public MarkDto build() {
		
		return new MarkDto(id, student.id, subject.id, mark);
	}
	
	

}
