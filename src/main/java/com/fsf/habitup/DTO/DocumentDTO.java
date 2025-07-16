package com.fsf.habitup.DTO;

import com.fsf.habitup.Enums.DocumentStatus;
import com.fsf.habitup.entity.Documents;

import java.time.LocalDateTime;

public class DocumentDTO {

    private Long id;

    private String idProof;         // base64-encoded string
    private String supportingDoc;   // base64-encoded string
    private String optionalDoc;     // base64-encoded string (nullable)

    private DocumentStatus status;

    private LocalDateTime uploadDate;

    private Long userId;

    // ====== Factory: Entity → DTO ======
    public static DocumentDTO fromEntity(Documents document) {
        DocumentDTO dto = new DocumentDTO();
        dto.setId(document.getId());
        dto.setIdProof(document.getIdProof());
        dto.setSupportingDoc(document.getSupportingDoc());
        dto.setOptionalDoc(document.getOptionalDoc());
        dto.setStatus(document.getStatus());
        dto.setUploadDate(document.getUploadDate());
        dto.setUserId(document.getUser().getUserId());
        return dto;
    }

    // ====== Optional: DTO → Entity (if needed) ======
    public Documents toEntity() {
        Documents doc = new Documents();
        doc.setId(this.id);
        doc.setIdProof(this.idProof);
        doc.setSupportingDoc(this.supportingDoc);
        doc.setOptionalDoc(this.optionalDoc);
        doc.setStatus(this.status);
        doc.setUploadDate(this.uploadDate);
        return doc;
    }

    // ====== Getters and Setters ======

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public String getSupportingDoc() {
        return supportingDoc;
    }

    public void setSupportingDoc(String supportingDoc) {
        this.supportingDoc = supportingDoc;
    }

    public String getOptionalDoc() {
        return optionalDoc;
    }

    public void setOptionalDoc(String optionalDoc) {
        this.optionalDoc = optionalDoc;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
