package system.sales.system_sales.ImageStorage.Impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import system.sales.system_sales.ImageStorage.ImageStorage;

@Component
public class FileSystemImageStorage implements ImageStorage {

    @Value("${storage.location}")
    private String storageLocation;

    // Define las extensiones permitidas
    private static final String[] ALLOWED_EXTENSIONS = { "jpg", "jpeg", "png", "gif" };
    // Define el tamaño máximo permitido en bytes (ejemplo: 5 MB)
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    @Override
    public String saveImage(MultipartFile image) {
        // Validar el tamaño del archivo
        if (image.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("File size exceeds the maximum limit of 5 MB");
        }

        // Validar la extensión del archivo
        String fileName = image.getOriginalFilename();
        if (fileName == null || !isAllowedExtension(fileName)) {
            throw new RuntimeException("Invalid file type. Allowed types are: jpg, jpeg, png, gif");
        }

        Path path = Paths.get(storageLocation, fileName);
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
        return fileName;
    }

    private boolean isAllowedExtension(String fileName) {
        String fileExtension = getFileExtension(fileName);
        for (String extension : ALLOWED_EXTENSIONS) {
            if (extension.equalsIgnoreCase(fileExtension)) {
                return true;
            }
        }
        return false;
    }

    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        return (lastIndexOfDot == -1) ? "" : fileName.substring(lastIndexOfDot + 1);
    }
}
