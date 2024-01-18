package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    private final static Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        try {
            logger.info("Создание нового факультета: {}", faculty);
            Faculty createdFaculty = facultyRepository.save(faculty);
            logger.info("Факультет успешно создан. ID нового факультета: {}", createdFaculty.getId());
            return createdFaculty;
        } catch (Exception e) {
            logger.error("Ошибка при создании нового факультета.", e);
            return null;
        }
    }

    public Optional<Faculty> findFaculty(Long id) {
        logger.info("Trying to find faculty with ID: {}", id);
        Optional<Faculty> faculty = facultyRepository.findFacultyById(id);
        if (faculty.isPresent()) {
            logger.info("Faculty found with ID {}: {}", id, faculty.get());
        } else {
            logger.info("Faculty not found with ID: {}", id);
        }
        return faculty;
    }

    public Optional<Faculty> updateFaculty(long id, Faculty faculty) {
        logger.info("Trying to refactor faculty with ID: {}", id);

        Optional<Faculty> facultyOptional = findFaculty(id);

        if (facultyOptional.isPresent()) {
            Faculty existingFaculty = facultyOptional.get();
            existingFaculty.setName(faculty.getName());
            existingFaculty.setColor(faculty.getColor());
            Faculty updatedFaculty = facultyRepository.save(existingFaculty);
            logger.info("Faculty ID {} successfully updated: {}", id, updatedFaculty);
            return Optional.of(updatedFaculty);
        } else {
            logger.info("Faculty not found with ID: {}", id);
            return Optional.empty();
        }
    }

    public void deleteFaculty(Long id) {
        logger.info("Trying to delete faculty with ID: {}", id);

        if (facultyRepository.existsById(id)) {
            facultyRepository.deleteById(id);
            logger.info("Faculty with ID {} successfully deleted.", id);
        } else {
            logger.warn("Faculty not found with ID: {}. Deletion failed.", id);
        }
    }


    public Faculty findByName(String name) {
        logger.info("Trying to find faculty by name: {}", name);

        Faculty faculty = facultyRepository.findFacultyByNameIgnoreCase(name);

        if (faculty != null) {
            logger.info("Faculty found by name {}: {}", name, faculty);
        } else {
            logger.info("No faculty found with name: {}", name);
        }
        return faculty;
    }

    public Faculty findByColor(String color) {
        logger.info("Trying to find faculty by color: {}", color);

        Faculty faculty = facultyRepository.findFacultyByColorIgnoreCase(color);

        if (faculty != null) {
            logger.info("Faculty found by color {}: {}", color, faculty);
        } else {
            logger.info("No faculty found with color: {}", color);
        }

        return faculty;
    }

    public Collection<Student> getStudentsOfFaculty(long id) {
        return findFaculty(id)
                .map(Faculty::getStudent)
                .orElse(Collections.emptyList());
    }

    public Collection<Faculty> filterColor(String color) {
        return facultyRepository.findAll()
                .stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
