package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public Student findStudent(@PathVariable long id) {
        return studentService.findStudent(id);
    }

    @PostMapping
    public Student createStudent(Student student) {
        return studentService.createStudent(student);
    }
}
