package com.fsf.habitup.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.fsf.habitup.entity.Meeting;

public class MeetingDTO {

    private Long meetingId;
    private Long adminId;
    private List<Long> participantIds;
    private boolean active;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // === Static Factory Method ===
    public static MeetingDTO fromEntity(Meeting meeting) {
        MeetingDTO dto = new MeetingDTO();
        dto.setMeetingId(meeting.getMeetingId());
        dto.setAdminId(meeting.getHost().getAdminId());
        dto.setParticipantIds(
                meeting.getParticipants().stream().map(p -> p.getUserId()).toList()
        );
        dto.setActive(meeting.isActive());
        dto.setStartTime(meeting.getStartTime());
        dto.setEndTime(meeting.getEndTime());
        return dto;
    }

    // === Getters and Setters ===
    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public List<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
