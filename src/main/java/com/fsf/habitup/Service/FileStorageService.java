package com.fsf.habitup.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file, String subFolder) {
        // Generate a unique name for the file
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Create full path: uploadDir/profile_photos/filename.jpg
        Path filePath = Paths.get(uploadDir, subFolder, fileName);

        try {
            Files.createDirectories(filePath.getParent()); // Make sure folder exists
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString(); // You can return just fileName or relative path if you prefer
        } catch (IOException e) {
            throw new RuntimeException("Could not store file: " + fileName, e);
        }
    }
}
