package com.fsf.habitup.Service;

import com.fsf.habitup.DTO.MeetingDTO;
import com.fsf.habitup.Repository.AdminRepository;
import com.fsf.habitup.Repository.MeetingRepository;
import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.entity.Admin;
import com.fsf.habitup.entity.Meeting;
import com.fsf.habitup.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    public MeetingDTO createMeeting(Long adminId) {
        Admin host = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Meeting meeting = new Meeting();
        meeting.setTopic("HabitUP Consultation");
        meeting.setMeetingCode(generateMeetingCode());
        meeting.setStartTime(LocalDateTime.now());
        meeting.setActive(true);
        meeting.setHost(host);

        return MeetingDTO.fromEntity(meetingRepository.save(meeting));
    }

    public void registerParticipant(Long userId, String meetingCode) {
        User participant = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Meeting meeting = meetingRepository.findByMeetingCode(meetingCode)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        if (!meeting.isActive()) {
            throw new RuntimeException("Meeting has ended.");
        }

        meeting.getParticipants().add(participant);
        meetingRepository.save(meeting);
    }

    public MeetingDTO getMeetingDetails(String meetingCode) {
        Meeting meeting = meetingRepository.findByMeetingCode(meetingCode)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        return MeetingDTO.fromEntity(meeting);
    }

    public void endMeeting(String meetingCode) {
        Meeting meeting = meetingRepository.findByMeetingCode(meetingCode)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        meeting.setActive(false);
        meeting.setEndTime(LocalDateTime.now());
        meetingRepository.save(meeting);
    }

    public List<MeetingDTO> getAllActiveMeetings() {
        return meetingRepository.findByIsActiveTrue()
                .stream()
                .map(MeetingDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public void cleanupOldRecordings() {
        List<Meeting> oldMeetings = meetingRepository.findAll()
                .stream()
                .filter(m -> m.getEndTime() != null && m.getEndTime().isBefore(LocalDateTime.now().minusDays(30)))
                .collect(Collectors.toList());

        for (Meeting meeting : oldMeetings) {
            meeting.setRecordingUrl(null);
        }
        meetingRepository.saveAll(oldMeetings);
    }

    private String generateMeetingCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
