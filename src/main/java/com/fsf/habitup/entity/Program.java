package com.fsf.habitup.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "programInformation")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "programId", nullable = false, unique = true)
    private Long program_id;

    @Column(name = "programName", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false, unique = true)
    private String description;

    @Column(name = "category", nullable = false, unique = false)
    private String category;

    @Column(name = "status", nullable = false, unique = false)
    private String status;

    @Column(name = "duration", nullable = false, unique = false)
    private Long durationInSeconds;

    @Column(name = "createdAt", nullable = false, unique = false)
    private Date createdAt;

    @Column(name = "updatedAt", nullable = false, unique = false)
    private Date updatedAt;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedback;

    /**
     * @return Long return the program_id
     */
    public Long getProgram_id() {
        return program_id;
    }

    /**
     * @param program_id the program_id to set
     */
    public void setProgram_id(Long program_id) {
        this.program_id = program_id;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return String return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return String return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return Long return the durationInSeconds
     */
    public Long getDurationInSeconds() {
        return durationInSeconds;
    }

    /**
     * @param durationInSeconds the durationInSeconds to set
     */
    public void setDurationInSeconds(Long durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    /**
     * @return Date return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return Date return the updatedAt
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return List<Feedback> return the feedback
     */
    public List<Feedback> getFeedback() {
        return feedback;
    }

    /**
     * @param feedback the feedback to set
     */
    public void setFeedback(List<Feedback> feedback) {
        this.feedback = feedback;
    }

}
