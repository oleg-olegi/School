package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional //bad practice
public class AvatarService {
    private final static Logger logger = LoggerFactory.getLogger(AvatarService.class);
    @Value("${path.to.avatars.folder}")
    private String avatarDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    @Autowired
    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }


    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        try {
            logger.info("Начало загрузки аватара для студента с ID {}.", studentId);

            Student student = studentService.findStudent(studentId);
            Path filePath = Path.of(avatarDir, student.getName() + "." + getExtension(Objects.requireNonNull(file.getOriginalFilename())));
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            try (
                    InputStream io = file.getInputStream();
                    OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                    BufferedInputStream bis = new BufferedInputStream(io, 1024);
                    BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
            ) {
                bis.transferTo(bos);
            }
            Avatar avatar = findAvatar(studentId);
            avatar.setStudent(student);
            avatar.setFilePath(filePath.toString());
            avatar.setData(file.getBytes());
            avatar.setFileSize(file.getSize());
            avatar.setMediaType(file.getContentType());
            avatar.setData(generateImagePreview(filePath));
            avatarRepository.save(avatar);

            logger.info("Аватар для студента с ID {} успешно загружен.", studentId);
        } catch (Exception e) {
            logger.error("Ошибка при загрузке аватара для студента с ID " + studentId, e);
        }
    }

    public void deleteAvatar(long studentId) {
        logger.info("Запрос на удаление студента с ID {}.", studentId);
        avatarRepository.deleteByStudentId(studentId);
        logger.info("Студент с ID {} успешно удален.", studentId);
    }

    public Avatar findAvatar(long studentId) {
        try {
            return avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
        } catch (Exception e) {
            logger.error("Ошибка при поиске аватара для студента с ID: {}", studentId, e);
            // Можно решить, что делать в случае ошибки, например, вернуть заглушку или бросить исключение
            return new Avatar();
        }
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            logger.info("Чтение изображения из файла: {}", filePath);

            // Читаем изображение из файла
            BufferedImage image = ImageIO.read(bis);

            logger.info("Изображение успешно прочитано из файла: {}", filePath);

            // Вычисляем высоту превью, сохраняя соотношение сторон
            int high = image.getHeight() / (image.getWidth() / 100);

            // Создаем новое изображение (превью) с шириной 100 и рассчитанной высотой
            BufferedImage preview = new BufferedImage(100, high, image.getType());
            Graphics2D graphics = preview.createGraphics();

            // Рисуем изображение на превью и освобождаем ресурсы
            graphics.drawImage(image, 0, 0, 100, high, null);
            graphics.dispose();

            logger.info("Превью успешно создано для изображения: {}", filePath);

            // Записываем превью в ByteArrayOutputStream с использованием того
            // же формата, что и исходное изображение
            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);

            logger.info("Превью успешно записано в ByteArrayOutputStream");

            // Возвращаем массив байт превью
            return baos.toByteArray();
        } catch (IOException e) {
            logger.error("Ошибка при создании превью для изображения: {}", filePath, e);
            throw e; // Перебрасываем исключение после логирования
        }
    }

    //пагинация JSON-объектов аватарок
    public List<Avatar> findAll(Integer pageNumber, Integer size) {

        logger.info("Поиск всех аватаров с использованием пагинации. Страница: {}, Размер: {}", pageNumber, size);

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, size);

        logger.info("Поиск успешно завершен. Найдено {} аватаров.",
                avatarRepository.findAll(pageRequest).getContent().size());

        return avatarRepository.findAll(pageRequest).getContent();
    }

    //пагинация превьюх аватарок. работает, но непонятно как
    public Page<Avatar> findAvatarPreviews(Integer pageNumber, Integer size) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, size);
        return avatarRepository.findAll(pageRequest);
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}

