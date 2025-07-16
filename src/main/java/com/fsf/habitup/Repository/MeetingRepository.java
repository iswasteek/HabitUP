package com.fsf.habitup.Repository;

import com.fsf.habitup.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    Optional<Meeting> findByMeetingCode(String code);

    List<Meeting> findByHost_AdminId(Long hostId);

    List<Meeting> findByParticipants_UserId(Long userId);

    List<Meeting> findByIsActiveTrue();
}
