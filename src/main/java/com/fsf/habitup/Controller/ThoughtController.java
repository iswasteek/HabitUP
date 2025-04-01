package com.fsf.habitup.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsf.habitup.Service.DailyThoughtService;
import com.fsf.habitup.entity.DailyThought;

@RestController
@RequestMapping("/habit/thoughts")
@PreAuthorize("hasRole('ADMIN')")
public class ThoughtController {

    private final DailyThoughtService dailyThoughtService;

    public ThoughtController(DailyThoughtService dailyThoughtService) {
        this.dailyThoughtService = dailyThoughtService;
    }

    @PreAuthorize("hasAuthority('MANAGE_THOUGHTS')")
    @PostMapping("/update-thought")
    public ResponseEntity<DailyThought> updateThought(@RequestParam String contentUrl,
                                                      @RequestParam String authorName) {
        DailyThought updatedThought = dailyThoughtService.updateThought(contentUrl, authorName);
        return ResponseEntity.ok(updatedThought);
    }

    // Endpoint to show the daily thought
    @PreAuthorize("hasAuthority('VIEW_THOUGHTS')")
    @GetMapping("/show-thought")
    public ResponseEntity<DailyThought> showThought() {
        DailyThought thought = dailyThoughtService.showThought();
        return ResponseEntity.ok(thought);
    }

    // Endpoint to delete the thought
    @PreAuthorize("hasAuthority('MANAGE_THOUGHTS')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteThought() {
        dailyThoughtService.deleteThought();
        return ResponseEntity.ok("Old thoughts deleted successfully.");
    }

}
