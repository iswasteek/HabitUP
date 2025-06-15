package com.fsf.habitup.Service;

import com.fsf.habitup.entity.Habit;

import java.util.List;

public interface HabitService {
    public Habit addHabitToUser(Long userId, Long systemHabitId, Integer customDuration);
    void deleteHabit(Long habitId);
    Habit updateHabit(Long habitId, Habit updatedHabit);
    List<Habit> showAllHabits();
    Habit showHabitById(Long habitId);
    List<Habit> getHabitsByUserId(Long userId);
    Habit createHabit(Habit habit);
    List<Habit> getUniversalDefaultHabits();
}
