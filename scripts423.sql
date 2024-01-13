-- � ���� ������� ������������ INNER JOIN ��� ���������� ������� Students � Faculties
--     �� ������� faculty_id, ������� ������������ ������� ���� � �����������.
--     �� �������� ��� � ������� �������� �� ������� Students � �������� ���������� �� ������� Faculties
SELECT Student.name AS student_name, Student.age AS student_age, Faculty.name AS faculty_name
FROM Student
         INNER JOIN Faculty ON Students.faculty_id = Faculty.faculty_id;

--� ���� ������� ������������ INNER JOIN ��� ���������� ������� Student � Avatar
-- �� ������� student_id, ������� ������������
-- ������� ���� � ���������. �� �������� ������ ��� � ������� �������� �� ������� Students
SELECT Student.name AS student_name, Student.age AS student_age
FROM Student
         INNER JOIN Avatars ON Student.student_id = Avatar.student_id;

