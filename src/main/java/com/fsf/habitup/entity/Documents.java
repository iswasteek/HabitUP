package com.fsf.habitup.entity;

import com.fsf.habitup.Enums.DocumentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
public class Documents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String idProof; // Base64 string of ID proof (required)

    @Column(columnDefinition = "LONGTEXT")
    private String supportingDoc; // Base64 string of supporting doc (required)

    @Column(columnDefinition = "LONGTEXT")
    private String optionalDoc; // Base64 string of optional doc (optional)

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    private LocalDateTime uploadDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ====== Getters & Setters ======

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
