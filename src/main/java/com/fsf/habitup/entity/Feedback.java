package com.fsf.habitup.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedbackId", nullable = false, unique = true)
    private Long feedback_id;

    @ManyToOne
    @JoinColumn(name = "programId", nullable = false)
    private Program program;

    @ManyToAny
    @JoinColumn(name = "userId", nullable = false)
    private List<User> user;

    @Column(name = "rating", nullable = false, unique = false)
    private float ratings;

    @Column(name = "Comments", nullable = false, unique = false)
    private String Comment;

    @Column(name = "Feedback_Date", nullable = false, unique = false)
    private Date feedbackDate;

    /**
     * @return Long return the feedback_id
     */
    public Long getFeedback_id() {
        return feedback_id;
    }

    /**
     * @param feedback_id the feedback_id to set
     */
    public void setFeedback_id(Long feedback_id) {
        this.feedback_id = feedback_id;
    }

    /**
     * @return Program return the program
     */
    public Program getProgram() {
        return program;
    }

    /**
     * @param program the program to set
     */
    public void setProgram(Program program) {
        this.program = program;
    }

    /**
     * @return List<User> return the user
     */
    public List<User> getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(List<User> user) {
        this.user = user;
    }

    /**
     * @return float return the ratings
     */
    public float getRatings() {
        return ratings;
    }

    /**
     * @param ratings the ratings to set
     */
    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    /**
     * @return String return the Comment
     */
    public String getComment() {
        return Comment;
    }

    /**
     * @param Comment the Comment to set
     */
    public void setComment(String Comment) {
        this.Comment = Comment;
    }

    /**
     * @return Date return the feedbackDate
     */
    public Date getFeedbackDate() {
        return feedbackDate;
    }

    /**
     * @param feedbackDate the feedbackDate to set
     */
    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

}
