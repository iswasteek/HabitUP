package com.fsf.habitup.Repository;

import com.fsf.habitup.Enums.UserType;
import com.fsf.habitup.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitRepo extends JpaRepository<Habit, Long> {

    // ✅ Fetch default habits for specific user types (Child, Adult, Elder)
    List<Habit> findByIsDefaultTrueAndUserType(UserType userType);

    // ✅ Fetch default universal habits (userType is null)
    List<Habit> findByIsDefaultTrueAndUserTypeIsNull();
}
