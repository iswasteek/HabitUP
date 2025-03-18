package com.fsf.habitup.entity;

import java.util.Date;

import com.fsf.habitup.Enums.HabitCompletionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "habitProgresss")
public class HabitProgress {

    // progressId
    // habitId
    // userId
    // progressDate
    // completionStatus
    // streakCount
    // motivationLevel
    // habitScore
    // notes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progressId", nullable = false, unique = true)
    private Long progressId;

    @Enumerated(EnumType.STRING)
    @Column(name = "completionStatus", nullable = false)
    private HabitCompletionStatus habitCompletionStatus;

    @Column(name = "streakCount", nullable = false)
    private int streakCount;

    @Column(name = "habitScore", nullable = false)
    private int habitScore;

    @Column(name = "progressStratingDate", nullable = false)
    private Date progressStartingDate;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    /**
     * @return Long return the progressId
     */
    public Long getProgressId() {
        return progressId;
    }

    /**
     * @param progressId the progressId to set
     */
    public void setProgressId(Long progressId) {
        this.progressId = progressId;
    }

    /**
     * @return HabitCompletionStatus return the habitCompletionStatus
     */
    public HabitCompletionStatus getHabitCompletionStatus() {
        return habitCompletionStatus;
    }

    /**
     * @param habitCompletionStatus the habitCompletionStatus to set
     */
    public void setHabitCompletionStatus(HabitCompletionStatus habitCompletionStatus) {
        this.habitCompletionStatus = habitCompletionStatus;
    }

    /**
     * @return int return the streakCount
     */
    public int getStreakCount() {
        return streakCount;
    }

    /**
     * @param streakCount the streakCount to set
     */
    public void setStreakCount(int streakCount) {
        this.streakCount = streakCount;
    }

    /**
     * @return int return the habitScore
     */
    public int getHabitScore() {
        return habitScore;
    }

    /**
     * @param habitScore the habitScore to set
     */
    public void setHabitScore(int habitScore) {
        this.habitScore = habitScore;
    }

    /**
     * @return Date return the progressStratingDate
     */
    public Date getProgressStratingDate() {
        return progressStartingDate;
    }

    /**
     * @param progressStratingDate the progressStratingDate to set
     */
    public void setProgressStratingDate(Date progressStartingDate) {
        this.progressStartingDate = progressStartingDate;
    }

    /**
     * @return Date return the progressStartingDate
     */
    public Date getProgressStartingDate() {
        return progressStartingDate;
    }

    /**
     * @param progressStartingDate the progressStartingDate to set
     */
    public void setProgressStartingDate(Date progressStartingDate) {
        this.progressStartingDate = progressStartingDate;
    }

    /**
     * @return User return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

}
