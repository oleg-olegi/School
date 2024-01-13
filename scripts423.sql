-- В этом запросе используется INNER JOIN для соединения таблицы Students и Faculties
--     по столбцу faculty_id, который представляет внешний ключ к факультетам.
--     Мы выбираем имя и возраст студента из таблицы Students и название факультета из таблицы Faculties
SELECT Student.name AS student_name, Student.age AS student_age, Faculty.name AS faculty_name
FROM Student
         INNER JOIN Faculty ON Students.faculty_id = Faculty.faculty_id;

--В этом запросе используется INNER JOIN для соединения таблицы Student и Avatar
-- по столбцу student_id, который представляет
-- внешний ключ к аватаркам. Мы выбираем только имя и возраст студента из таблицы Students
SELECT Student.name AS student_name, Student.age AS student_age
FROM Student
         INNER JOIN Avatars ON Student.student_id = Avatar.student_id;

