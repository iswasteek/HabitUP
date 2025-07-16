package com.fsf.habitup.Controller;

import com.fsf.habitup.DTO.DocumentDTO;
import com.fsf.habitup.entity.Documents;
import com.fsf.habitup.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocuments(
            @RequestParam("userId") Long userId,
            @RequestParam("idProof") MultipartFile idProof,
            @RequestParam("supportingDoc") MultipartFile supportingDoc,
            @RequestParam(value = "optionalDoc", required = false) MultipartFile optionalDoc) {

        try {
            String idProofBase64 = Base64.getEncoder().encodeToString(idProof.getBytes());
            String supportingDocBase64 = Base64.getEncoder().encodeToString(supportingDoc.getBytes());
            String optionalDocBase64 = optionalDoc != null && !optionalDoc.isEmpty()
                    ? Base64.getEncoder().encodeToString(optionalDoc.getBytes())
                    : null;

            documentService.uploadDocuments(userId, idProofBase64, supportingDocBase64, optionalDocBase64);

            return ResponseEntity.ok("Documents uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error uploading documents: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<DocumentDTO> getUserDocuments(@PathVariable Long userId) {
        Documents document = documentService.getUserDocuments(userId);
        return ResponseEntity.ok(DocumentDTO.fromEntity(document)); // returns base64 fields
    }
}
