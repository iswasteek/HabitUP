package com.fsf.habitup.Controller;

import com.fsf.habitup.DTO.MeetingDTO;
import com.fsf.habitup.Service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    // ✅ 1. Admin starts a meeting
    @PostMapping("/start")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<MeetingDTO> startMeeting(@RequestParam Long adminId) {
        MeetingDTO meeting = meetingService.createMeeting(adminId);
        return ResponseEntity.ok(meeting);
    }

    // ✅ 2. User joins a meeting
    @PostMapping("/join")
    public ResponseEntity<String> joinMeeting(@RequestParam Long userId, @RequestParam String meetingId) {
        meetingService.registerParticipant(userId, meetingId);
        return ResponseEntity.ok("User joined meeting successfully.");
    }

    // ✅ 3. Get meeting details (for joining)
    @GetMapping("/{meetingId}")
    public ResponseEntity<MeetingDTO> getMeetingDetails(@PathVariable String meetingId) {
        return ResponseEntity.ok(meetingService.getMeetingDetails(meetingId));
    }

    // ✅ 4. End a meeting (admin only)
    @PostMapping("/end")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> endMeeting(@RequestParam String meetingId) {
        meetingService.endMeeting(meetingId);
        return ResponseEntity.ok("Meeting ended successfully.");
    }

    // ✅ 5. Optional: Get all active meetings (admin panel)
    @GetMapping("/active")
    public ResponseEntity<List<MeetingDTO>> getActiveMeetings() {
        return ResponseEntity.ok(meetingService.getAllActiveMeetings());
    }

    // ✅ 6. Optional: Delete old recordings (cleanup)
    @DeleteMapping("/recordings/cleanup")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> cleanupOldRecordings() {
        meetingService.cleanupOldRecordings();
        return ResponseEntity.ok("Old recordings deleted.");
    }
}
