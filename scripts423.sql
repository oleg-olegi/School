-- � ���� ������� ������������ INNER JOIN ��� ���������� ������� Students � Faculties
--     �� ������� faculty_id, ������� ������������ ������� ���� � �����������.
--     �� �������� ��� � ������� �������� �� ������� Students � �������� ���������� �� ������� Faculties
SELECT student.name AS student_name, student.age AS student_age, faculty.name AS faculty_name
FROM student
         LEFT JOIN faculty ON student.faculty_id = faculty.faculty_id;

--� ���� ������� ������������ INNER JOIN ��� ���������� ������� Student � Avatar
-- �� ������� student_id, ������� ������������
-- ������� ���� � ���������. �� �������� ������ ��� � ������� �������� �� ������� Students
SELECT student.name AS student_name, student.age AS student_age
FROM student
         INNER JOIN avatar ON student.student_id = avatar.student_id;

