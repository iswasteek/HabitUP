package com.fsf.habitup.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fsf.habitup.Enums.HabitCategory;

import jakarta.persistence.*;

@Entity
@Table(name = "habit")
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habitId", nullable = false, unique = true)
    private Long habitId;

    @Column(name = "habitName", nullable = false, length = 50)
    private String habitName;

    @OneToMany(mappedBy = "progressId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HabitProgress> progressRecords = new ArrayList<>();

    @Column(name = "HabitDescription", nullable = false)
    private String habitDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 50)
    private HabitCategory habitCategory;

    @Column(name = "habitDuration", nullable = false)
    private int habitDuration;

    @OneToMany(mappedBy = "progressId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HabitProgress> habitProgress;

    @ManyToMany(mappedBy = "habits")
    @JsonBackReference
    private Set<User> users = new HashSet<>(); // Each habit belongs to one user

    @Column(name = "reminderTime")
    private LocalTime reminderTime;  // LocalTime for storing time like "08:00"

    @Column(name = "total_completions", nullable = false)
    private long totalCompletions = 0;

    @Column(name = "current_streak", nullable = false)
    private int currentStreak = 0;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    public long getTotalCompletions() {
        return totalCompletions;
    }

    public void setTotalCompletions(long totalCompletions) {
        this.totalCompletions = totalCompletions;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * @return Long return the habitId
     */
    public Long getHabitId() {
        return habitId;
    }

    /**
     * @param habitId the habitId to set
     */
    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }

    /**
     * @return String return the habitName
     */
    public String getHabitName() {
        return habitName;
    }

    /**
     * @param habitName the habitName to set
     */
    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    /**
     * @return HabitDescription return the habitDescription
     */
    public String getHabitDescription() {
        return habitDescription;
    }

    /**
     * @param habitDescription the habitDescription to set
     */
    public void setHabitDescription(String habitDescription) {
        this.habitDescription = habitDescription;
    }

    /**
     * @return HabitCategory return the habitCategory
     */
    public HabitCategory getHabitCategory() {
        return habitCategory;
    }

    /**
     * @param habitCategory the habitCategory to set
     */
    public void setHabitCategory(HabitCategory habitCategory) {
        this.habitCategory = habitCategory;
    }

    /**
     * @return int return the habitDuration
     */
    public int getHabitDuration() {
        return habitDuration;
    }

    /**
     * @param habitDuration the habitDuration to set
     */
    public void setHabitDuration(int habitDuration) {
        this.habitDuration = habitDuration;
    }

    /**
     * @return List<HabitProgress> return the habitProgress
     */
    public List<HabitProgress> getHabitProgress() {
        return habitProgress;
    }

    /**
     * @param habitProgress the habitProgress to set
     */
    public void setHabitProgress(List<HabitProgress> habitProgress) {
        this.habitProgress = habitProgress;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public List<HabitProgress> getProgressRecords() {
        return progressRecords;
    }

    public void setProgressRecords(List<HabitProgress> progressRecords) {
        this.progressRecords = progressRecords;
    }

    public LocalTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalTime reminderTime) {
        this.reminderTime = reminderTime;
    }
}
