package com.fsf.habitup.Controller;

import com.fsf.habitup.Service.HabitServiceImpl;
import com.fsf.habitup.entity.Habit;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
public class HabitController {
    private final HabitServiceImpl habitService;

    public HabitController(HabitServiceImpl habitService) {
        this.habitService = habitService;
    }

    @PostMapping("/assign/{userId}/{habitId}")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<Habit> assignHabitToUser(
            @PathVariable Long userId,
            @PathVariable Long habitId,
            @RequestParam(required = false) Integer duration) {
        Habit assignedHabit = habitService.addHabitToUser(userId, habitId, duration);
        return ResponseEntity.ok(assignedHabit);
    }

    @DeleteMapping("/{habitId}")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<Void> deleteHabit(@PathVariable Long habitId) {
        habitService.deleteHabit(habitId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{habitId}")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<Habit> updateHabit(
            @PathVariable Long habitId,
            @RequestBody Habit updatedHabit) {
        Habit habit = habitService.updateHabit(habitId, updatedHabit);
        return ResponseEntity.ok(habit);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<List<Habit>> getAllHabits() {
        List<Habit> habits = habitService.showAllHabits();
        return ResponseEntity.ok(habits);
    }

    @GetMapping("/{habitId}")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<Habit> getHabitById(@PathVariable Long habitId) {
        Habit habit = habitService.showHabitById(habitId);
        return ResponseEntity.ok(habit);
    }

    // Get habits assigned to a user
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<List<Habit>> getHabitsByUserId(@PathVariable Long userId) {
        List<Habit> userHabits = habitService.getHabitsByUserId(userId);
        return ResponseEntity.ok(userHabits);
    }

    // Create a new habit
    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<Habit> createHabit(@RequestBody Habit habit) {
        Habit createdHabit = habitService.createHabit(habit);
        return ResponseEntity.ok(createdHabit);
    }

    @GetMapping("/universal-defaults")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<List<Habit>> getUniversalDefaultHabits() {
        List<Habit> universalHabits = habitService.getUniversalDefaultHabits();
        return ResponseEntity.ok(universalHabits);
    }



}
