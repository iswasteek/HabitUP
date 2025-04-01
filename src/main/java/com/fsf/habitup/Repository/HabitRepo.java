package com.fsf.habitup.Repository;

import com.fsf.habitup.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitRepo extends JpaRepository<Habit, Long> {

}
