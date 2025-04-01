package com.fsf.habitup.Controller;

import com.fsf.habitup.Service.FeedbackService;
import com.fsf.habitup.entity.Feedback;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habit/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }


    @PreAuthorize("hasAuthority('MANAGE_FEEDBACK')")
    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        Feedback createdFeedback = feedbackService.createFeedback(feedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback);
    }

    // Get feedback by ID (Assuming only users with VIEW_FEEDBACK can view it)
    @PreAuthorize("hasAuthority('VIEW_FEEDBACK')")
    @GetMapping("/{feedbackId}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long feedbackId) {
        Feedback feedback = feedbackService.getFeedbackById(feedbackId);
        return ResponseEntity.ok(feedback);
    }

    // Get feedback by program (Assuming only users with VIEW_FEEDBACK can view)
    @PreAuthorize("hasAuthority('VIEW_FEEDBACK')")
    @GetMapping("/program/{programId}")
    public ResponseEntity<List<Feedback>> getFeedbackByProgram(@PathVariable Long programId) {
        List<Feedback> feedbackList = feedbackService.getFeedbackByProgram(programId);
        return ResponseEntity.ok(feedbackList);
    }

    // Get feedback by user (Assuming only users with VIEW_FEEDBACK can view)
    @PreAuthorize("hasAuthority('VIEW_FEEDBACK')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Feedback>> getFeedbackByUser(@PathVariable Long userId) {
        List<Feedback> feedbackList = feedbackService.getFeedbackByUser(userId);
        return ResponseEntity.ok(feedbackList);
    }

    // Update feedback (Assuming only users with MANAGE_FEEDBACK can update)
    @PreAuthorize("hasAuthority('MANAGE_FEEDBACK')")
    @PutMapping("/{feedbackId}")
    public ResponseEntity<Feedback> updateFeedback(
            @PathVariable Long feedbackId,
            @RequestBody Feedback updatedFeedback) {
        Feedback feedback = feedbackService.updateFeedback(feedbackId, updatedFeedback);
        return ResponseEntity.ok(feedback);
    }

    // Delete feedback (Assuming only users with MANAGE_FEEDBACK can delete)
    @PreAuthorize("hasAuthority('MANAGE_FEEDBACK')")
    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.noContent().build();
    }

    // Get average rating for a program (Assuming only users with VIEW_FEEDBACK can view)
    @PreAuthorize("hasAuthority('VIEW_FEEDBACK')")
    @GetMapping("/program/{programId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long programId) {
        double averageRating = feedbackService.getAverageRatingForProgram(programId);
        return ResponseEntity.ok(averageRating);
    }

    // Get recent feedback (Assuming only users with VIEW_FEEDBACK can view)
    @PreAuthorize("hasAuthority('VIEW_FEEDBACK')")
    @GetMapping("/recent")
    public ResponseEntity<List<Feedback>> getRecentFeedback(
            @RequestParam(defaultValue = "7") int days) {
        List<Feedback> recentFeedback = feedbackService.getRecentFeedback(days);
        return ResponseEntity.ok(recentFeedback);
    }
}
