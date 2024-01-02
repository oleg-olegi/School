package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    @Test
    void createStudentTest() throws Exception {
        long id = 1L;
        String name = "Oleg";
        int age = 15;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("age", age);

        Student student = new Student();
        student.setAge(age);
        student.setName(name);
        student.setId(id);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.age").value(age));

        verify(studentRepository, times(1)).save(argThat(savedStudent ->
                savedStudent.getName().equals(name) && savedStudent.getAge() == age
        ));
    }

    @Test
    void getStudentTest() throws Exception {
        Long id = 1L;
        String name = "Oleg";
        int age = 15;

        Student student = new Student(id, name, age);

        when(studentRepository.findStudentById(id)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(status().isOk());
    }

    @Test
    void editStudentTest() throws Exception {
        Long id = 1L;
        String name = "Oleg";
        int age = 15;
        int newAge = 16;

        Student student = new Student(id, name, age);
        Student changedStudent = new Student(id, name, newAge);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("age", newAge);

        when(studentService.updateStudent(student)).thenReturn(changedStudent);
        when(studentRepository.findStudentById(id)).thenReturn(changedStudent);
        when(studentRepository.save(student)).thenReturn(changedStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(newAge));
    }
    @Test
    public void testDeleteStudent() throws Exception {
        long id = 1;
        String name = "Bob";
        int age = 37;
        Student student = new Student(id, name, age);

        when(studentRepository.findById(any())).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
