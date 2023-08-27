package telran.spring.college.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.spring.college.dto.*;
import telran.spring.college.entity.*;
import telran.spring.college.repo.*;
import telran.spring.exceptions.NotFoundException;
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CollegeServiceImpl implements CollegeServcie {
final StudentRepository studentRepo;
final SubjectRepository subjectRepo;
final LecturerRepository lecturerRepo;
final MarkRepository markRepo;
@Value("${app.person.id.min:100000}")
long minId;
@Value("${app.person.id.max:999999}")
long maxId;
	@Override
	@Transactional(readOnly=false)
	public PersonDto addStudent(PersonDto person) {
		if(person.getId() == null) {
			person.setId(getStudentUniqueId());
		}
		
		Student student = Student.of(person);
		if (studentRepo.existsById(student.getId())) {
			throw new IllegalStateException("Student with given id already exists" + student.getId());
		}
		PersonDto res = studentRepo.save(student).build();
		log.debug("Student added {}", res);
		return res;
	}

	private  Long getStudentUniqueId() {
		return getId(studentRepo);
	}

	private <T> Long getId(JpaRepository<T, Long> repository) {
		long res = 0;
		do {
			res = new Random().nextLong(minId, maxId + 1);
		}while(repository.existsById(res));
		return res;
	}

	@Override
	@Transactional(readOnly=false)
	public PersonDto addLecturer(PersonDto person) {
		if(person.getId() == null) {
			person.setId(getLecturerUniqueId());
		}
		Lecturer lecturer = Lecturer.of(person);
		if (lecturerRepo.existsById(lecturer.getId())) {
			throw new IllegalStateException("Lecturer with given id already exists" + lecturer.getId());
		}
		PersonDto res = lecturerRepo.save(lecturer).build();
		log.debug("Lecturer added {}", res);
		return res;
	}

	private Long getLecturerUniqueId() {
		
		return getId(lecturerRepo);
	}

	@Override
	@Transactional(readOnly=false)
	public SubjectDto addSubject(SubjectDto subject) {
		if (subjectRepo.existsById(subject.getId())) {
			throw new IllegalStateException("Subject with given id exists " + subject.getId());
		}
		Lecturer lecturer = null;
		Long lecturerId = subject.getLecturerId();
		if(lecturerId != null) {
			lecturer = lecturerRepo.findById(lecturerId).orElseThrow(() ->
			new NotFoundException(String.format("Lecturer with id %d doen't exist", lecturerId)));
		}
		Subject subjectEntity = Subject.of(subject);
		subjectEntity.setLecturer(lecturer);
		SubjectDto res = subjectRepo.save(subjectEntity).build();
		log.debug("subject added {}", res);
		return res;
	}

	@Override
	@Transactional(readOnly=false)
	public MarkDto addMark(MarkDto mark) {
		long studentId = mark.getStudentId();
		Student student = studentRepo.findById(studentId).orElseThrow(() ->
		new NotFoundException(String.format("Student with id %d doesn't exist in DB", studentId)));
		String subjectId = mark.getSubjectId();
		Subject subject = subjectRepo.findById(subjectId)
				.orElseThrow(() -> new NotFoundException(String.format("Subject with id %s doesn't exist in DB", subjectId)));
		Mark markEntity = new Mark(student, subject, mark.getMark());
		MarkDto res = markRepo.save(markEntity).build();
		log.debug("Mark added {}", res);
		return res;
	}

}
