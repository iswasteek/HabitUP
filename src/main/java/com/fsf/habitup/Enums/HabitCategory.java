package com.fsf.habitup.Enums;

public enum HabitCategory {

    COMMUNICATING_EFFECTIVELY("Communicating Effectively"),
    CONFLICT_RESOLUTION("Conflict Resolution"),
    EATING_HEALTHY("Eating Healthy"),
    ENGAGE_IN_HOBBY("Engage in Hobby"),
    EXERCISE_AND_YOGA("Exercise & Yoga"),
    GOAL_SETTING("Goal Setting"),
    HYGIENE("Hygiene"),
    LEARNING_SKILL_DEVELOPMENT("Learning & Skill Development"),
    MAINTAIN_GOOD_POSTURE("Maintain Good Posture"),
    MEDITATION("Meditation"),
    MENTAL_HEALTH("Mental Health"),
    MINIMIZING_DISTRACTIONS("Minimizing Distractions"),
    PREPARE_FOR_THE_NEXT_DAY("Prepare for the Next Day"),
    PRIORITIZING_TASKS("Prioritizing Tasks"),
    READING("Reading"),
    SELF_REFLECTION_AND_GRATITUDE("Self Reflection & Gratitude"),
    SLEEPING_WELL("Sleeping Well"),
    SPENDING_QUALITY_TIME("Spending Quality Time"),
    STAY_HYDRATED("Stay Hydrated"),
    TIME_MANAGEMENT("Time Management");

    private final String displayName;

    HabitCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
