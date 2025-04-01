package com.fsf.habitup.Repository;

import com.fsf.habitup.entity.Habit;
import com.fsf.habitup.entity.HabitProgress;
import com.fsf.habitup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitProgressRepository extends JpaRepository<HabitProgress, Long> {
    Optional<HabitProgress> findByHabitAndUserAndProgressStartingDate(@Param("habit") Habit habit,
                                                                      @Param("user") User user,
                                                                      @Param("progressStartingDate") LocalDate progressStartingDate);
    List<HabitProgress> findByHabitAndUserAndProgressStartingDateBetween(
            Habit habit,
            User user,
            LocalDate startDate,
            LocalDate endDate);

    List<HabitProgress> findByHabitAndUserOrderByProgressStartingDateDesc(Habit habit, User user);
    List<HabitProgress> findByHabitAndUserOrderByProgressStartingDateAsc(Habit habit, User user);
    List<HabitProgress> findByHabitAndUser(Habit habit, User user);
}
