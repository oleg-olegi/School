-- В этом запросе используется INNER JOIN для соединения таблицы Students и Faculties
--     по столбцу faculty_id, который представляет внешний ключ к факультетам.
--     Мы выбираем имя и возраст студента из таблицы Students и название факультета из таблицы Faculties
SELECT student.name AS student_name, student.age AS student_age, faculty.name AS faculty_name
FROM student
         LEFT JOIN faculty ON student.faculty_id = faculty.faculty_id;

--В этом запросе используется INNER JOIN для соединения таблицы Student и Avatar
-- по столбцу student_id, который представляет
-- внешний ключ к аватаркам. Мы выбираем только имя и возраст студента из таблицы Students
SELECT student.name AS student_name, student.age AS student_age
FROM student
         INNER JOIN avatar ON student.student_id = avatar.student_id;

