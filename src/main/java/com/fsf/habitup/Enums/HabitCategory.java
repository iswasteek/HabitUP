package com.fsf.habitup.Enums;

public enum HabitCategory {
    MEDITATION("Meditation"),
    READING("Reading"),
    EXERCISE_AND_YOGA("Exercise & Yoga"),
    EATING_HEALTHY("Eating Healthy"),
    SLEEPING_WELL("Sleeping Well"),
    HYGIENE("Hygiene"),
    MENTAL_HEALTH("Mental Health"),
    TIME_MANAGEMENT("Time Management"),
    GOAL_SETTING("Goal Setting"),
    LEARNING_SKILL_DEVELOPMENT("Learning & Skill Development"),
    MINIMIZING_DISTRACTIONS("Minimizing Distractions"),
    PRIORITIZING_TASKS("Prioritizing Tasks"),
    COMMUNICATING_EFFECTIVELY("Communicating Effectively"),
    SPENDING_QUALITY_TIME("Spending Quality Time"),
    CONFLICT_RESOLUTION("Conflict Resolution"),
    STAY_HYDRATED("Stay Hydrated"),
    MAINTAIN_GOOD_POSTURE("Maintain Good Posture"),
    ENGAGE_IN_HOBBY("Engage in Hobby"),
    PREPARE_FOR_THE_NEXT_DAY("Prepare for the Next Day"),
    SELF_REFLECTION_AND_GRATITUDE("Self Reflection & Gratitude");

    private final String displayName;

    HabitCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
