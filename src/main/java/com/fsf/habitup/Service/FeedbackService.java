package com.fsf.habitup.Service;

import com.fsf.habitup.Exception.ResourceNotFoundException;
import com.fsf.habitup.Repository.FeedbackRepository;
import com.fsf.habitup.Repository.ProgramRepository;
import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.entity.Feedback;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final ProgramRepository programRepository;
    private final UserRepository userRepository;

    public FeedbackService(FeedbackRepository feedbackRepository,
                               ProgramRepository programRepository,
                               UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.programRepository = programRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Feedback createFeedback(Feedback feedback) {
        // Validate program exists
        programRepository.findById(feedback.getProgram().getProgram_id())
                .orElseThrow(() -> new ResourceNotFoundException("Program not found"));

        // Validate user exists
        feedback.getUser().forEach(user ->
                userRepository.findById(user.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"))
        );

        feedback.setFeedbackDate(new Date());
        return feedbackRepository.save(feedback);
    }


    public Feedback getFeedbackById(Long feedbackId) {
        return feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + feedbackId));
    }

    public List<Feedback> getFeedbackByProgram(Long programId) {
        if (!programRepository.existsById(programId)) {
            throw new ResourceNotFoundException("Program not found with id: " + programId);
        }
        return feedbackRepository.findByProgram_ProgramId(programId);
    }

    public List<Feedback> getFeedbackByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        // Return all feedback for user
        return feedbackRepository.findByUser_UserIdOrderByFeedbackDateDesc(userId);
    }

    @Transactional
    public Feedback updateFeedback(Long feedbackId, Feedback updatedFeedback) {
        Feedback existingFeedback = getFeedbackById(feedbackId);

        existingFeedback.setRatings(updatedFeedback.getRatings());
        existingFeedback.setComment(updatedFeedback.getComment());

        return feedbackRepository.save(existingFeedback);
    }

    @Transactional
    public void deleteFeedback(Long feedbackId) {
        Feedback feedback = getFeedbackById(feedbackId);
        feedbackRepository.delete(feedback);
    }

    public double getAverageRatingForProgram(Long programId) {
        if (!programRepository.existsById(programId)) {
            throw new ResourceNotFoundException("Program not found with id: " + programId);
        }

        Double average = feedbackRepository.calculateAverageRatingByProgramId(programId);

        return average != null ? average : 0.0;
    }

    @Transactional
    public List<Feedback> getRecentFeedback(int days) {
        if (days < 1) {
            throw new IllegalArgumentException("Days parameter must be positive");
        }
        // 2. Calculate cutoff date (timezone-aware)
        LocalDate cutoff = LocalDate.now().minusDays(days);
        Instant cutoffInstant = cutoff.atStartOfDay(ZoneId.systemDefault()).toInstant();

        // 3. Fetch feedback with date filtering
        return feedbackRepository.findByFeedbackDateAfter(Date.from(cutoffInstant));
    }
}
