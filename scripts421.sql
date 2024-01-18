-- ¬озраст студента не может быть меньше 16 лет:
ALTER TABLE student
    ADD CONSTRAINT check_age CHECK (age >= 16);

--»мена студентов должны быть уникальными и не равны нулю:
ALTER TABLE student
    ADD CONSTRAINT unique_name
        UNIQUE (name);

--ѕара "значение названи€" - "цвет факультета" должна быть уникальной:
ALTER TABLE faculty
    ADD CONSTRAINT unique_name_color
        UNIQUE (name, color);

--ѕри создании студента без возраста ему автоматически должно присваиватьс€ 20 лет:
ALTER TABLE student
    ADD CONSTRAINT default_age
        DEFAULT 20 FOR age;


