package com.fsf.habitup.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

public interface HabitProgressService {
    void recordDailyCompletion(Long habitId, Long userId, LocalDate date, boolean completed);
    void markHabitDone(Long habitId, Long userId); // Marks today's habit as done
    void toggleCompletionStatus(Long habitId, Long userId, LocalDate date);

    boolean getCompletionStatus(Long habitId, Long userId, LocalDate date);
    boolean getTodayStatus(Long habitId, Long userId);
    Map<LocalDate, Boolean> getWeeklyStatus(Long habitId, Long userId);
    Map<YearMonth, Boolean> getMonthlyStatus(Long habitId, Long userId);

    int getCurrentStreak(Long habitId, Long userId);
    int getLongestStreak(Long habitId, Long userId);
    double calculateConsistencyScore(Long habitId, Long userId);

    Map<LocalDate, Boolean> getMonthlyCompletionCalendar(Long habitId, Long userId);
    Map<String, Object> getProgressStatistics(Long habitId, Long userId);
    double getSuccessRate(Long habitId, Long userId);

    double predictHabitAdoption(Long habitId, Long userId); // 0-100% likelihood
    LocalDate predictHabitMasteryDate(Long habitId, Long userId);

  //  void scheduleReminder(Long habitId, Long userId, String reminderTime);
  //  void checkAndSendReminders();

    String exportProgressData(Long habitId, Long userId); // CSV/JSON
    void importProgressData(Long habitId, Long userId, String data);

    Map<LocalDate, String> getDailyNotes(Long habitId, Long userId);
    void addProgressNote(Long habitId, Long userId, LocalDate date, String note);
   // void adjustHabitDifficulty(Long habitId, int newDifficulty);


}
