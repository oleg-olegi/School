package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;



@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> findStudent(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundedStudent = studentService.updateStudent(student);
        if (foundedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundedStudent);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("ageBetween/{min}/{max}")
    public ResponseEntity findByAgeBetween(@PathVariable int min, @PathVariable int max) {
        Collection<Student> students = studentService.findByAgeBetween(min, max);
        if (students.isEmpty()) {
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/ageFilter/{age}")
    public Collection<Student> ageFilteredStudents(@PathVariable int age) {

        return studentService.filterAge(age);
    }
}
