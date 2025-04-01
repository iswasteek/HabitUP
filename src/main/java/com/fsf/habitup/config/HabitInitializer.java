package com.fsf.habitup.config;

import com.fsf.habitup.Enums.HabitCategory;
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
                        createHabit("Morning Meditation", HabitCategory.MEDITATION, "Meditation", 15),
                        createHabit("Daily Reading", HabitCategory.READING, "Reading", 30),
                        createHabit("Yoga Session", HabitCategory.EXERCISE_AND_YOGA,"Exercise & Yoga", 30),
                        createHabit("Healthy Meal", HabitCategory.EATING_HEALTHY, "Eating Healthy", 20),
                        createHabit("Quality Sleep", HabitCategory.SLEEPING_WELL, "Sleeping Well", 0),
                        createHabit("Personal Hygiene", HabitCategory.HYGIENE, "Hygiene", 15),
                        createHabit("Mental Health Check", HabitCategory.MENTAL_HEALTH, "Mental Health", 10),
                        createHabit("Time Blocking", HabitCategory.TIME_MANAGEMENT, "Time Management", 15),
                        createHabit("Goal Review", HabitCategory.GOAL_SETTING, "Goal Setting", 20),
                        createHabit("Skill Development", HabitCategory.LEARNING_SKILL_DEVELOPMENT, "Learning & Skill Development", 30),
                        createHabit("Focus Time", HabitCategory.MINIMIZING_DISTRACTIONS, "Minimizing Distractions", 25),
                        createHabit("Task Prioritization", HabitCategory.PRIORITIZING_TASKS, "Prioritizing Tasks", 10),
                        createHabit("Active Listening", HabitCategory.COMMUNICATING_EFFECTIVELY, "Communicating Effectively", 0),
                        createHabit("Family Time", HabitCategory.SPENDING_QUALITY_TIME, "Spending Quality Time", 60),
                        createHabit("Conflict Resolution", HabitCategory.CONFLICT_RESOLUTION, "Conflict Resolution", 15),
                        createHabit("Water Intake", HabitCategory.STAY_HYDRATED, "Stay Hydrated", 0),
                        createHabit("Posture Check", HabitCategory.MAINTAIN_GOOD_POSTURE, "Maintain Good Posture", 5),
                        createHabit("Hobby Time", HabitCategory.ENGAGE_IN_HOBBY, "Engage in Hobby", 45),
                        createHabit("Next Day Prep", HabitCategory.PREPARE_FOR_THE_NEXT_DAY, "Prepare for the Next Day", 15),
                        createHabit("Gratitude Journal", HabitCategory.SELF_REFLECTION_AND_GRATITUDE, "Self Reflection & Gratitude", 10)
                );

                habitRepo.saveAll(defaultHabits);
                System.out.println("Successfully initialized all default habits.");
            }
        }

        private Habit createHabit(String name, HabitCategory category, String description, int duration) {
            Habit habit = new Habit();
            habit.setHabitName(name);
            habit.setHabitCategory(category);
            habit.setHabitDescription(description);
            habit.setHabitDuration(duration);
            return habit;
        }

}
