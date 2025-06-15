package com.fsf.habitup.config;

import com.fsf.habitup.Enums.HabitCategory;
import com.fsf.habitup.Enums.UserType;
import com.fsf.habitup.Repository.HabitRepo;
import com.fsf.habitup.entity.Habit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class HabitInitializer implements CommandLineRunner {

    private final HabitRepo habitRepo;

    public HabitInitializer(HabitRepo habitRepo) {
        this.habitRepo = habitRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (habitRepo.count() == 0) {
            List<Habit> defaultHabits = Arrays.asList(
                    // Children
                    createHabit("Balgopal Gita Chanting", HabitCategory.LEARNING_SKILL_DEVELOPMENT, "Introduce children to the divine wisdom of the Bhagavad Gita through simple chanting sessions with melodic tunes.", 20, UserType.Child),
                    createHabit("Meditation & Yoga for Kids", HabitCategory.EXERCISE_AND_YOGA, "Child-friendly yoga poses and short meditation to improve focus and balance.", 20, UserType.Child),
                    createHabit("Memory Hacks for Kids", HabitCategory.LEARNING_SKILL_DEVELOPMENT, "Vedic memory techniques to boost recall and academic learning.", 15, UserType.Child),
                    createHabit("Moral Story Telling", HabitCategory.MENTAL_HEALTH, "Stories from Indian scriptures to teach ethics and values.", 15, UserType.Child),

                    // Adults
                    createHabit("Adolescent Psychology Sessions", HabitCategory.MENTAL_HEALTH, "Guidance for navigating adolescence with Vedic and psychological principles.", 30, UserType.Adult),
                    createHabit("Aura Balancing", HabitCategory.MENTAL_HEALTH, "Cleanse and strengthen your energy field for emotional and mental well-being.", 20, UserType.Adult),
                    createHabit("Gita in Yogic Practices", HabitCategory.MEDITATION, "Apply Bhagavad Gita's teachings through yoga and mindfulness.", 30, UserType.Adult),
                    createHabit("Stress Management", HabitCategory.MENTAL_HEALTH, "Modern + ancient stress relief practices.", 25, UserType.Adult),

                    // Elders
                    createHabit("Gentle Yoga & Meditation", HabitCategory.EXERCISE_AND_YOGA, "Yoga and meditation for seniors to improve mobility and peace of mind.", 20, UserType.Elder),
                    createHabit("Gita Discourses", HabitCategory.SELF_REFLECTION_AND_GRATITUDE, "In-depth exploration of Bhagavad Gita teachings.", 30, UserType.Elder),
                    createHabit("Scripture Study", HabitCategory.LEARNING_SKILL_DEVELOPMENT, "Guided reading and application of sacred texts.", 25, UserType.Elder),
                    createHabit("Lifestyle Balance", HabitCategory.EATING_HEALTHY, "Manage age-related issues with holistic wellness practices.", 20, UserType.Elder),

                    // Universal (userType = null)
                    createHabit("Hydration Reminder", HabitCategory.STAY_HYDRATED, "Stay hydrated throughout the day with regular water intake.", 0, null),
                    createHabit("Daily Reflection", HabitCategory.SELF_REFLECTION_AND_GRATITUDE, "Reflect on your day and express gratitude.", 10, null),
                    createHabit("Digital Detox", HabitCategory.MINIMIZING_DISTRACTIONS, "Limit screen time and engage in offline activities.", 15, null),
                    createHabit("Healthy Snacking", HabitCategory.EATING_HEALTHY, "Replace junk food with nutritious snacks.", 10, null),
                    createHabit("Goal Planning", HabitCategory.GOAL_SETTING, "Set and plan daily or weekly goals.", 15, null),
                    createHabit("Journaling Emotions", HabitCategory.MENTAL_HEALTH, "Track and understand emotional patterns through journaling.", 10, null),
                    createHabit("Time Audit", HabitCategory.TIME_MANAGEMENT, "Review how your time is spent to increase productivity.", 10, null)
            );

            habitRepo.saveAll(defaultHabits);
            System.out.println("✅ Default habits initialized.");
        }
    }

    private Habit createHabit(String name, HabitCategory category, String description, int duration, UserType userType) {
        Habit habit = new Habit();
        habit.setHabitName(name);
        habit.setHabitCategory(category);
        habit.setHabitDescription(description);
        habit.setHabitDuration(duration);
        habit.setUserType(userType);
        habit.setDefault(true);
        return habit;
    }
}
