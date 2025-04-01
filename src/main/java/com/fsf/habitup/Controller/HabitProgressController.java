package com.fsf.habitup.Controller;

import com.fsf.habitup.Service.HabitProgressImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

@RestController
@RequestMapping("/habit/habitProgress")
public class HabitProgressController {
    @Autowired
    private HabitProgressImpl habitProgressService;


    @PreAuthorize("hasAuthority('MANAGE_HABIT_PROGRESS')")
    @PostMapping("/{habitId}/users/{userId}/completion")
    public ResponseEntity<Void> recordDailyCompletion(
            @PathVariable Long habitId,
            @PathVariable Long userId,
            @RequestParam boolean completed) {
        try {
            habitProgressService.recordDailyCompletion(habitId, userId, LocalDate.now(), completed);
            return ResponseEntity.ok().build(); // Return 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Return 500 Internal Server Error in case of exception
        }
    }

    // Mark habit as completed for the current day
    @PreAuthorize("hasAuthority('MANAGE_HABIT_PROGRESS')")
    @PostMapping("/{habitId}/users/{userId}/mark-done")
    public ResponseEntity<Void> markHabitDone(
            @PathVariable Long habitId,
            @PathVariable Long userId) {
        try {
            habitProgressService.markHabitDone(habitId, userId);
            return ResponseEntity.ok().build(); // Return 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Return 500 Internal Server Error in case of exception
        }
    }

    // Toggle completion status
    @PreAuthorize("hasAuthority('MANAGE_HABIT_PROGRESS')")
    @PostMapping("/{habitId}/users/{userId}/toggle-completion")
    public ResponseEntity<Void> toggleCompletionStatus(
            @PathVariable Long habitId,
            @PathVariable Long userId,
            @RequestParam LocalDate date) {
        try {
            habitProgressService.toggleCompletionStatus(habitId, userId, date);
            return ResponseEntity.ok().build(); // Return 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Return 500 Internal Server Error in case of exception
        }
    }

    // Get completion status for a specific date
    @PreAuthorize("hasAuthority('VIEW_HABIT_PROGRESS')")
    @GetMapping("/{habitId}/users/{userId}/completion-status")
    public ResponseEntity<Boolean> getCompletionStatus(
            @PathVariable Long habitId,
            @PathVariable Long userId,
            @RequestParam LocalDate date) {
        try {
            boolean completionStatus = habitProgressService.getCompletionStatus(habitId, userId, date);
            return ResponseEntity.ok(completionStatus); // Return completion status for the given date
        } catch (Exception e) {
            return ResponseEntity.status(500).body(false); // Return error response if something goes wrong
        }
    }

    // Get today's completion status
    @PreAuthorize("hasAuthority('VIEW_HABIT_PROGRESS')")
    @GetMapping("/{habitId}/users/{userId}/today-status")
    public ResponseEntity<Boolean> getTodayStatus(
            @PathVariable Long habitId,
            @PathVariable Long userId) {
        try {
            boolean todayStatus = habitProgressService.getTodayStatus(habitId, userId);
            return ResponseEntity.ok(todayStatus); // Return today's completion status
        } catch (Exception e) {
            return ResponseEntity.status(500).body(false); // Return error response if something goes wrong
        }
    }

    // Get weekly completion status (last 7 days)
    @PreAuthorize("hasAuthority('VIEW_HABIT_PROGRESS')")
    @GetMapping("/{habitId}/users/{userId}/weekly-status")
    public ResponseEntity<Map<LocalDate, Boolean>> getWeeklyStatus(
            @PathVariable Long habitId,
            @PathVariable Long userId) {
        try {
            Map<LocalDate, Boolean> weeklyStatus = habitProgressService.getWeeklyStatus(habitId, userId);
            return ResponseEntity.ok(weeklyStatus); // Return weekly completion status
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Return error response if something goes wrong
        }
    }

    // Get monthly completion status (last 6 months)
    @PreAuthorize("hasAuthority('VIEW_HABIT_PROGRESS')")
    @GetMapping("/{habitId}/users/{userId}/monthly-status")
    public ResponseEntity<Map<YearMonth, Boolean>> getMonthlyStatus(
            @PathVariable Long habitId,
            @PathVariable Long userId) {
        try {
            Map<YearMonth, Boolean> monthlyStatus = habitProgressService.getMonthlyStatus(habitId, userId);
            return ResponseEntity.ok(monthlyStatus); // Return monthly completion status
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Return error response if something goes wrong
        }
    }

    // Get current streak for a habit
    @PreAuthorize("hasAuthority('VIEW_HABIT_PROGRESS')")
    @GetMapping("/{habitId}/users/{userId}/current-streak")
    public ResponseEntity<Integer> getCurrentStreak(
            @PathVariable Long habitId,
            @PathVariable Long userId) {
        try {
            int currentStreak = habitProgressService.getCurrentStreak(habitId, userId);
            return ResponseEntity.ok(currentStreak); // Return the current streak
        } catch (Exception e) {
            return ResponseEntity.status(500).body(0); // Return error response if something goes wrong
        }
    }

    // Get longest streak for a habit
    @PreAuthorize("hasAuthority('VIEW_HABIT_PROGRESS')")
    @GetMapping("/{habitId}/users/{userId}/longest-streak")
    public ResponseEntity<Integer> getLongestStreak(
            @PathVariable Long habitId,
            @PathVariable Long userId) {
        try {
            int longestStreak = habitProgressService.getLongestStreak(habitId, userId);
            return ResponseEntity.ok(longestStreak); // Return the longest streak
        } catch (Exception e) {
            return ResponseEntity.status(500).body(0); // Return error response if something goes wrong
        }
    }

    // Get consistency score for a habit
    @PreAuthorize("hasAuthority('VIEW_HABIT_PROGRESS')")
    @GetMapping("/{habitId}/users/{userId}/consistency-score")
    public ResponseEntity<Double> getConsistencyScore(
            @PathVariable Long habitId,
            @PathVariable Long userId) {
        try {
            double consistencyScore = habitProgressService.calculateConsistencyScore(habitId, userId);
            return ResponseEntity.ok(consistencyScore); // Return the consistency score
        } catch (Exception e) {
            return ResponseEntity.status(500).body(0.0); // Return error response if something goes wrong
        }
    }

    // Get the monthly completion calendar for the habit and user
    @PreAuthorize("hasAuthority('VIEW_HABIT_PROGRESS')")
    @GetMapping("/{habitId}/users/{userId}/monthly-completion")
    public ResponseEntity<Map<LocalDate, Boolean>> getMonthlyCompletionCalendar(
            @PathVariable Long habitId,
            @PathVariable Long userId) {
        try {
            Map<LocalDate, Boolean> completionCalendar = habitProgressService.getMonthlyCompletionCalendar(habitId, userId);
            return ResponseEntity.ok(completionCalendar); // Return the completion calendar for the current month
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Return error response if something goes wrong
        }
    }

    // Get overall progress statistics for the habit and user
    @PreAuthorize("hasAuthority('VIEW_HABIT_PROGRESS')")
    @GetMapping("/{habitId}/users/{userId}/progress-statistics")
    public ResponseEntity<Map<String, Object>> getProgressStatistics(
            @PathVariable Long habitId,
            @PathVariable Long userId) {
        try {
            Map<String, Object> statistics = habitProgressService.getProgressStatistics(habitId, userId);
            return ResponseEntity.ok(statistics); // Return the overall progress statistics
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Return error response if something goes wrong
        }
    }

    // Get success rate for a habit
    @PreAuthorize("hasAuthority('VIEW_HABIT_PROGRESS')")
    @GetMapping("/{habitId}/user/{userId}/success-rate")
    public ResponseEntity<Double> getSuccessRate(@PathVariable Long habitId, @PathVariable Long userId) {
        double successRate = habitProgressService.getSuccessRate(habitId, userId);
        return ResponseEntity.ok(successRate);
    }

    // Predict habit adoption based on streak length
    @PreAuthorize("hasAuthority('VIEW_HABIT_PROGRESS')")
    @GetMapping("/{habitId}/user/{userId}/predict-adoption")
    public ResponseEntity<Double> predictHabitAdoption(@PathVariable Long habitId, @PathVariable Long userId) {
        double adoptionProbability = habitProgressService.predictHabitAdoption(habitId, userId);
        return ResponseEntity.ok(adoptionProbability);
    }

    // Predict habit mastery date based on current progress
    @PreAuthorize("hasAuthority('VIEW_HABIT_PROGRESS')")
    @GetMapping("/{habitId}/user/{userId}/predict-mastery-date")
    public ResponseEntity<LocalDate> predictHabitMasteryDate(@PathVariable Long habitId, @PathVariable Long userId) {
        LocalDate masteryDate = habitProgressService.predictHabitMasteryDate(habitId, userId);
        return ResponseEntity.ok(masteryDate);
    }

    // Export progress data to CSV
    @PreAuthorize("hasAuthority('EXPORT_HABIT_PROGRESS')")
    @GetMapping("/{habitId}/user/{userId}/export-progress")
    public ResponseEntity<String> exportProgressData(@PathVariable Long habitId, @PathVariable Long userId) {
        String csvData = habitProgressService.exportProgressData(habitId, userId);
        return ResponseEntity.ok(csvData);
    }

    // Import progress data from CSV
    @PreAuthorize("hasAuthority('IMPORT_HABIT_PROGRESS')")
    @PostMapping("/{habitId}/user/{userId}/import-progress")
    public ResponseEntity<Void> importProgressData(@PathVariable Long habitId, @PathVariable Long userId, @RequestBody String data) {
        habitProgressService.importProgressData(habitId, userId, data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Get daily notes for a specific habit and user
    @PreAuthorize("hasAuthority('VIEW_HABIT_PROGRESS')")
    @GetMapping("/{habitId}/user/{userId}/daily-notes")
    public ResponseEntity<Map<LocalDate, String>> getDailyNotes(@PathVariable Long habitId, @PathVariable Long userId) {
        Map<LocalDate, String> dailyNotes = habitProgressService.getDailyNotes(habitId, userId);
        return ResponseEntity.ok(dailyNotes);
    }

    // Add a note to a specific day's habit progress
    @PreAuthorize("hasAuthority('MANAGE_HABIT_PROGRESS')")
    @PostMapping("/{habitId}/user/{userId}/add-progress-note")
    public ResponseEntity<Void> addProgressNote(@PathVariable Long habitId, @PathVariable Long userId, @RequestParam LocalDate date, @RequestParam String note) {
        habitProgressService.addProgressNote(habitId, userId, date, note);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
