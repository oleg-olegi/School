package ru.hogwarts.school.model;

public class Student {
    private Long id;
    private final String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
