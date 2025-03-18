package com.fsf.habitup.Service;

import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fsf.habitup.Repository.DailyThoughtRepository;
import com.fsf.habitup.entity.DailyThought;

@Service
public class DailyThoughtService {

    private final DailyThoughtRepository dailyThoughtRepository;

    public DailyThoughtService(DailyThoughtRepository dailyThoughtRepository) {
        this.dailyThoughtRepository = dailyThoughtRepository;
    }

    public DailyThought updateThought(String contentUrl, String authorName) {
        // Create a new DailyThought object
        DailyThought newThought = new DailyThought();

        // Set the fields of the new thought
        newThought.setContentURL(contentUrl);
        newThought.setAuthorName(authorName);
        newThought.setUpdatedAt(new Date());

        // Save the new thought to the database
        return dailyThoughtRepository.save(newThought);
    }

    public DailyThought showThought() {
        // Get the most recent thought based on the update time
        return dailyThoughtRepository.findTopByOrderByUpdatedAtDesc();
    }

    @Scheduled(fixedRate = 3600000) // Runs every hour
    public void deleteThought() {
        List<DailyThought> allThoughts = dailyThoughtRepository.findAll();
        Date now = new Date();

        for (DailyThought thought : allThoughts) {
            long diffInMillies = now.getTime() - thought.getUpdatedAt().getTime();
            long diffInHours = diffInMillies / (1000 * 60 * 60);

            // Delete thoughts older than 24 hours
            if (diffInHours >= 24) {
                dailyThoughtRepository.delete(thought); // Remove thought from DB
            }
        }
    }
}
