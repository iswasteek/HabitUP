package com.fsf.habitup.Service;

import com.fsf.habitup.Enums.DocumentStatus;
import com.fsf.habitup.Repository.DocumentsRepository;
import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.entity.Documents;
import com.fsf.habitup.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    private DocumentsRepository documentRepository;

    @Autowired
    private UserRepository userRepository;


    public String uploadDocuments(Long userId, String idProofBase64, String supportingDocBase64, String optionalDocBase64) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if documents already exist
        Optional<Documents> existingDocsOpt = documentRepository.findByUser_UserId(userId);

        if (existingDocsOpt.isPresent()) {
            Documents existingDocs = existingDocsOpt.get();

            if (existingDocs.getStatus() != DocumentStatus.REJECTED) {
                throw new RuntimeException("Documents already uploaded. Status: " + existingDocs.getStatus()
                        + ". You can only re-upload if the documents were rejected.");
            }

            // Overwrite existing rejected documents
            existingDocs.setIdProof(idProofBase64);
            existingDocs.setSupportingDoc(supportingDocBase64);
            existingDocs.setOptionalDoc(optionalDocBase64);
            existingDocs.setStatus(DocumentStatus.PENDING); // reset status to pending on resubmission
            existingDocs.setUploadDate(LocalDateTime.now());

            documentRepository.save(existingDocs);
            return "Rejected documents re-uploaded successfully.";
        }

        // No previous documents – create new
        Documents documents = new Documents();
        documents.setUser(user);
        documents.setIdProof(idProofBase64);
        documents.setSupportingDoc(supportingDocBase64);
        documents.setOptionalDoc(optionalDocBase64); // can be null
        documents.setStatus(DocumentStatus.PENDING);
        documents.setUploadDate(LocalDateTime.now());

        documentRepository.save(documents);
        return "Documents uploaded successfully.";
    }

    public Documents getUserDocuments(Long userId) {
        return documentRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Documents not found for user."));
    }
}
