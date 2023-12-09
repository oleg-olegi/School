package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;

@Service
public class StudentService {
    private final HashMap<Long, Student> students;
    private Long counterID = 0L;

    public StudentService() {
        this.students = new HashMap<>();
    }

    public Student createStudent(Student student) {
        student.setId(++counterID);
        students.put(counterID, student);
        return student;
    }

    public Student findStudent(Long counterID) {
        return students.get(counterID);
    }

    public Student updateStudent(Student student) {
        students.put(student.getId(), student);
        return student;
    }

    public Student deleteStudent(Long id) {
        return students.remove(id);
    }
}
