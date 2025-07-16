package com.fsf.habitup.Repository;

import com.fsf.habitup.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Get full conversation between two users
    @Query("SELECT m FROM Message m WHERE " +
            "(m.sender.userId = :user1Id AND m.receiver.userId = :user2Id) OR " +
            "(m.sender.userId = :user2Id AND m.receiver.userId = :user1Id) " +
            "ORDER BY m.sentAt ASC")
    List<Message> findConversation(Long user1Id, Long user2Id);

    // Inbox
    List<Message> findByReceiver_UserIdOrderBySentAtDesc(Long receiverId);

    // Sent messages
    List<Message> findBySender_UserIdOrderBySentAtDesc(Long senderId);

    // All messages sorted by sentAt
    List<Message> findAllByOrderBySentAtDesc();

    // Unread messages
    List<Message> findByReceiver_UserIdAndIsReadFalse(Long receiverId);

    // Messages older than 10 days (for auto deletion)
    List<Message> findBySentAtBeforeAndAutoDeletedFalse(LocalDateTime cutoffDate);

    // Messages sent to coach with no reply in 10 days (pending replies)
    @Query("SELECT m FROM Message m WHERE " +
            "m.receiver.userId = :coachId AND " +
            "m.sender.userType <> 'Doctor' AND " +
            "m.sentAt <= :cutoffDate AND " +
            "m.autoDeleted = false AND " +
            "NOT EXISTS (SELECT r FROM Message r " +
            "WHERE r.sender.userId = :coachId AND r.receiver.userId = m.sender.userId " +
            "AND r.sentAt > m.sentAt)")
    List<Message> findPendingRepliesForCoach(Long coachId, LocalDateTime cutoffDate);

}
