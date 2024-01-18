package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final static Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        try {
            logger.info("Создание нового студента: {}", student);
            Student createStudent = studentRepository.save(student);
            logger.info("Студент успешно создан. ID нового студента: {}", createStudent.getId());
            return createStudent;
        } catch (Exception e) {
            logger.error("Ошибка при создании нового студента.", e);
            return null;
        }
    }

    public Optional<Student> findStudent(Long id) {
        logger.info("Trying to find student with ID: {}.", id);
        Optional<Student> foundedStudent = studentRepository.findStudentById(id);
        if (foundedStudent.isPresent()) {
            logger.info("Student found with ID {}: {}", id, foundedStudent.get());
        } else {
            logger.info("Student not found with ID: {}", id);
        }
        return foundedStudent;
    }

    public Optional<Student> updateStudent(long id, Student student) {
        logger.info("Trying to refactor student with ID: {}", id);
        Optional<Student> foundedStudent = studentRepository.findStudentById(id);
        if (foundedStudent.isPresent()) {
            Student existingStudent = foundedStudent.get();
            existingStudent.setName(student.getName());
            existingStudent.setAge(student.getAge());
            Student updatedStudent = studentRepository.save(existingStudent);
            logger.info("Student ID {} successfully updated {}.", id, student);
            return Optional.of(updatedStudent);
        } else {
            logger.info("Student ID {} not found.", id);
            return Optional.empty();
        }
    }

    public void deleteStudent(Long id) {
        logger.info("Request for delete student with ID {}.", id);
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            logger.info("Student with ID {} successfully deleted.", id);
        } else {
            logger.warn("Faculty not found with ID: {}. Deletion failed.", id);
        }
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Request for collection students with age between MIN {} & MAX {}.", min, max);
        Collection<Student> students = studentRepository.findByAgeBetween(min, max);
        if (students.isEmpty()) {
            logger.info("No students found with age between {} and {}.", min, max);
        } else {
            logger.info("{} students found with age between {} and {}.", students.size(), min, max);
        }
        return students;
    }


    public Optional<Faculty> getStudentsFaculty(long id) {
        logger.info("Request to the faculty for a student with ID {}.", id);
        Optional<Faculty> facultyOptional = studentRepository.findStudentById(id)
                .map(Student::getFaculty);
        logger.info("Returned faculty {}", facultyOptional.orElse(null));
        return facultyOptional;
    }

    public Collection<Student> filterAge(int age) {
        return studentRepository.findAll()
                .stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    public Integer getAmount() {
        return studentRepository.getAmountOfStudents();
    }

    public Integer getAverageAge() {
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }
}
