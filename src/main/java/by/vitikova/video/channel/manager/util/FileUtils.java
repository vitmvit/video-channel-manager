package by.vitikova.video.channel.manager.util;

import by.vitikova.video.channel.manager.exception.OperationException;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@UtilityClass
public class FileUtils {

    /**
     * Преобразует MultipartFile в строку формата Base64.
     *
     * @param file MultipartFile для преобразования
     * @return строка, представляющая файл в формате Base64
     * @throws IOException если ошибка во время чтения файла
     */
    public static String convertMultipartFileToBase64(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException ex) {
            throw new OperationException("Avatar error encode: " + ex.getMessage());
        }
    }
}