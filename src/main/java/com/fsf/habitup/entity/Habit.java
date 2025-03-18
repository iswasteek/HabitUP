package com.fsf.habitup.entity;

import java.util.List;

import com.fsf.habitup.Enums.HabitCategory;
import com.fsf.habitup.Enums.HabitDescription;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "habit")
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habitId", nullable = false, unique = true)
    private Long habitId;

    @Column(name = "habitName", nullable = false)
    private String habitName;

    @Enumerated(EnumType.STRING)
    @Column(name = "HabitDescription", nullable = false)
    private HabitDescription habitDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private HabitCategory habitCategory;

    @Column(name = "habitDuration", nullable = false)
    private int habitDuration;

    @OneToMany(mappedBy = "progressId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HabitProgress> habitProgress;

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
    public HabitDescription getHabitDescription() {
        return habitDescription;
    }

    /**
     * @param habitDescription the habitDescription to set
     */
    public void setHabitDescription(HabitDescription habitDescription) {
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

}
