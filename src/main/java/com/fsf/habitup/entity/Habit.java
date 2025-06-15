package com.fsf.habitup.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fsf.habitup.Enums.HabitCategory;
import com.fsf.habitup.Enums.UserType;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "habit")
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habitId", nullable = false, unique = true)
    private Long habitId;

    @Column(name = "habitName", nullable = false, length = 50)
    private String habitName;

    @Column(name = "habitDescription", nullable = false)
    private String habitDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 50)
    private HabitCategory habitCategory;

    @Column(name = "habitDuration", nullable = false)
    private int habitDuration;

    @Column(name = "reminderTime")
    private LocalTime reminderTime;

    @Column(name = "total_completions", nullable = false)
    private long totalCompletions = 0;

    @Column(name = "current_streak", nullable = false)
    private int currentStreak = 0;

    @ManyToMany(mappedBy = "habits")
    @JsonBackReference
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HabitProgress> habitProgressList = new ArrayList<>();

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", length = 20)
    private UserType userType;

    // --- Getters and Setters ---

    public Long getHabitId() {
        return habitId;
    }

    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public String getHabitDescription() {
        return habitDescription;
    }

    public void setHabitDescription(String habitDescription) {
        this.habitDescription = habitDescription;
    }

    public HabitCategory getHabitCategory() {
        return habitCategory;
    }

    public void setHabitCategory(HabitCategory habitCategory) {
        this.habitCategory = habitCategory;
    }

    public int getHabitDuration() {
        return habitDuration;
    }

    public void setHabitDuration(int habitDuration) {
        this.habitDuration = habitDuration;
    }

    public LocalTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalTime reminderTime) {
        this.reminderTime = reminderTime;
    }

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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public List<HabitProgress> getHabitProgressList() {
        return habitProgressList;
    }

    public void setHabitProgressList(List<HabitProgress> habitProgressList) {
        this.habitProgressList = habitProgressList;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
