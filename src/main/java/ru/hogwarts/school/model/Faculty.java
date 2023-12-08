package ru.hogwarts.school.model;


public class Faculty {
    private Long id;
    private final String name;
    private String color;

    public Faculty(String name, String color) {
        this.name = name;
        this.color = color;
    }

}
