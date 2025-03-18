package com.fsf.habitup.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsf.habitup.entity.DailyThought;

@Repository
public interface DailyThoughtRepository extends JpaRepository<DailyThought, Long> {
    DailyThought findTopByOrderByUpdatedAtDesc();

}
