-- ������� �������� �� ����� ���� ������ 16 ���:
ALTER TABLE student
    ADD CONSTRAINT check_age CHECK (age >= 16);

--����� ��������� ������ ���� ����������� � �� ����� ����:
ALTER TABLE student
    ADD CONSTRAINT unique_name
        UNIQUE (name);

--���� "�������� ��������" - "���� ����������" ������ ���� ����������:
ALTER TABLE faculty
    ADD CONSTRAINT unique_name_color
        UNIQUE (name, color);

--��� �������� �������� ��� �������� ��� ������������� ������ ������������� 20 ���:
ALTER TABLE student
    ADD CONSTRAINT default_age
        DEFAULT 20 FOR age;


