package com.fsf.habitup.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fsf.habitup.Enums.DocumentStatus;
import com.fsf.habitup.Repository.DocumentsRepository;
import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.entity.Documents;
import com.fsf.habitup.entity.User;

@Service
public class DocumentService {

    @Autowired
    private DocumentsRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    private final String uploadDir = "uploads"; // or read from application.properties

    public String storeFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        Files.createDirectories(filePath.getParent()); // create if not exists
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + fileName;
    }

    public void uploadDocument(Long userId, MultipartFile file, String documentType) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String fileUrl = storeFile(file);

        Documents document = new Documents();
        document.setDocumentName(file.getOriginalFilename());
        document.setDocumentType(documentType);
        document.setDocumentUrl(fileUrl);
        document.setStatus(DocumentStatus.PENDING);
        document.setUser(user);

        documentRepository.save(document);
    }

    public List<Documents> fetchDocumentsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        return documentRepository.findByUser_UserId(userId);
    }
}
