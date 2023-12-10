package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import javax.xml.stream.StreamFilter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

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
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public Student editStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @DeleteMapping
    public Student deleteStudent(@PathVariable long id) {
        return studentService.deleteStudent(id);
    }

    //1. Добавить фильтрацию студентов по возрасту.
    //
    //Для этого в StudentController добавить эндпоинт, который принимает число (возраст — поле age)
    // и возвращает список студентов, у которых совпал возраст с переданным числом.
    @GetMapping("/ageFilter/{age}")
    public Collection<Student> ageFilteredStudents(@PathVariable int age) {
        return studentService.filterAge(age);
    }
}
