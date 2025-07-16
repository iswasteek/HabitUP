package com.fsf.habitup.Service;

import com.fsf.habitup.Enums.SenderType;
import com.fsf.habitup.Enums.SubscriptionType;
import com.fsf.habitup.Enums.UserType;
import com.fsf.habitup.Repository.MessageRepository;
import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.entity.Message;
import com.fsf.habitup.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    public void sendMessage(Long senderId, Long receiverId, String content, SenderType senderType) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));

        // Premium check
        if (isRegularUser(sender.getUserType()) &&
                (sender.getSubscriptionType() == null || sender.getSubscriptionType() != SubscriptionType.PREMIUM)) {
            throw new RuntimeException("Only premium users can send messages.");
        }

        // Admin logic
        if (senderType == SenderType.ADMIN && receiver.getUserType() != UserType.Doctor) {
            throw new RuntimeException("Admin can only message Coaches.");
        }

        // Coach logic
        if (senderType == SenderType.COACH) {
            boolean isPremiumUser = isRegularUser(receiver.getUserType()) &&
                    receiver.getSubscriptionType() == SubscriptionType.PREMIUM;

            boolean isAdmin = receiver.getUserType() == UserType.Admin;

            if (!isPremiumUser && !isAdmin) {
                throw new RuntimeException("Coaches can only message premium users or admins.");
            }
        }

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setSenderType(senderType);
        message.setSentAt(LocalDateTime.now());
        message.setRead(false);
        message.setAutoDeleted(false);

        messageRepository.save(message);
    }

    public List<Message> getConversationBetween(Long user1Id, Long user2Id) {
        return messageRepository.findConversation(user1Id, user2Id);
    }

    public List<Message> getMessagesForCoach(Long coachId) {
        return messageRepository.findByReceiver_UserIdOrderBySentAtDesc(coachId);
    }

    public List<Message> getMessagesForUser(Long userId) {
        return messageRepository.findByReceiver_UserIdOrderBySentAtDesc(userId);
    }

    public List<Message> getMessagesSentBy(Long userId) {
        return messageRepository.findBySender_UserIdOrderBySentAtDesc(userId);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAllByOrderBySentAtDesc();
    }

    public List<Message> getUnreadMessages(Long receiverId) {
        return messageRepository.findByReceiver_UserIdAndIsReadFalse(receiverId);
    }

    public List<Message> getPendingRepliesForCoach(Long coachId) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(10);
        return messageRepository.findPendingRepliesForCoach(coachId, cutoff);
    }

    public void markAsRead(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        message.setRead(true);
        messageRepository.save(message);
    }

    // Scheduled task to auto-delete old messages (after 10 days)
    @Scheduled(cron = "0 0 2 * * ?") // Every day at 2 AM
    public void deleteOldMessages() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(10);
        List<Message> oldMessages = messageRepository.findBySentAtBeforeAndAutoDeletedFalse(cutoff);

        for (Message msg : oldMessages) {
            msg.setContent("[Auto Deleted]");
            msg.setAutoDeleted(true);
        }
        messageRepository.saveAll(oldMessages);
    }

    // Helper method
    private boolean isRegularUser(UserType userType) {
        return userType == UserType.Adult || userType == UserType.Child || userType == UserType.Elder;
    }
}