package com.fsf.habitup.Controller;

import com.fsf.habitup.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fsf.habitup.Enums.SenderType;
import com.fsf.habitup.entity.Message;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // ============= Admin APIs =============

    @PostMapping("/admin/send")
    public ResponseEntity<String> sendMessageAsAdmin(
            @RequestParam Long adminId,
            @RequestParam Long coachId,
            @RequestParam String content) {
        messageService.sendMessage(adminId, coachId, content, SenderType.ADMIN);
        return ResponseEntity.ok("Message sent from Admin to Coach.");
    }

    @GetMapping("/admin/conversation")
    public ResponseEntity<List<Message>> getAdminCoachConversation(
            @RequestParam Long adminId,
            @RequestParam Long coachId) {
        return ResponseEntity.ok(messageService.getConversationBetween(adminId, coachId));
    }

    // ============= User APIs =============

    @PostMapping("/user/send")
    public ResponseEntity<String> sendMessageAsUser(
            @RequestParam Long userId,
            @RequestParam Long coachId,
            @RequestParam String content) {
        messageService.sendMessage(userId, coachId, content, SenderType.USER);
        return ResponseEntity.ok("Message sent to Coach.");
    }

    @GetMapping("/user/inbox/{userId}")
    public ResponseEntity<List<Message>> getUserInbox(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.getMessagesForUser(userId));
    }

    @GetMapping("/user/sent/{userId}")
    public ResponseEntity<List<Message>> getUserSentMessages(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.getMessagesSentBy(userId));
    }

    @GetMapping("/user/conversation")
    public ResponseEntity<List<Message>> getUserCoachConversation(
            @RequestParam Long userId,
            @RequestParam Long coachId) {
        return ResponseEntity.ok(messageService.getConversationBetween(userId, coachId));
    }

    // ============= Coach APIs =============

    @PostMapping("/coach/reply")
    public ResponseEntity<String> replyAsCoach(
            @RequestParam Long coachId,
            @RequestParam Long userId,
            @RequestParam String content) {
        messageService.sendMessage(coachId, userId, content, SenderType.COACH);
        return ResponseEntity.ok("Coach reply sent.");
    }

    @GetMapping("/coach/inbox/{coachId}")
    public ResponseEntity<List<Message>> getCoachInbox(@PathVariable Long coachId) {
        return ResponseEntity.ok(messageService.getMessagesForUser(coachId));
    }

    @GetMapping("/coach/pending/{coachId}")
    public ResponseEntity<List<Message>> getCoachPendingReplies(@PathVariable Long coachId) {
        return ResponseEntity.ok(messageService.getPendingRepliesForCoach(coachId));
    }

    @GetMapping("/coach/conversation")
    public ResponseEntity<List<Message>> getCoachUserConversation(
            @RequestParam Long coachId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(messageService.getConversationBetween(coachId, userId));
    }

    // ============= Common APIs =============

    @PutMapping("/mark-read/{messageId}")
    public ResponseEntity<String> markMessageAsRead(@PathVariable Long messageId) {
        messageService.markAsRead(messageId);
        return ResponseEntity.ok("Message marked as read.");
    }

    @GetMapping("/unread/{receiverId}")
    public ResponseEntity<List<Message>> getUnreadMessages(@PathVariable Long receiverId) {
        return ResponseEntity.ok(messageService.getUnreadMessages(receiverId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }
}


