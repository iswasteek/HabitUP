package com.fsf.habitup.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meetingId;

    private String topic;
    private String meetingCode; // Unique code for joining (like Zoom)
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private boolean isActive;

    @ManyToOne
    private Admin host;

    @ManyToMany
    private Set<User> participants = new HashSet<>();

    private String recordingUrl; // Optional link to recording

    // Constructors
    public Meeting() {}

    public Meeting(String topic, String meetingCode, LocalDateTime startTime, Admin host) {
        this.topic = topic;
        this.meetingCode = meetingCode;
        this.startTime = startTime;
        this.isActive = true;
        this.host = host;
    }

    // Getters and Setters

    public Long getMeetingId() { return meetingId; }
    public void setMeetingId(Long meetingId) { this.meetingId = meetingId; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getMeetingCode() { return meetingCode; }
    public void setMeetingCode(String meetingCode) { this.meetingCode = meetingCode; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public Admin getHost() { return host; }
    public void setHost(Admin host) { this.host = host; }

    public Set<User> getParticipants() { return participants; }
    public void setParticipants(Set<User> participants) { this.participants = participants; }

    public String getRecordingUrl() { return recordingUrl; }
    public void setRecordingUrl(String recordingUrl) { this.recordingUrl = recordingUrl; }
}