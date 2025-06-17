package com.fsf.habitup.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fsf.habitup.DTO.AdminMailRequest;
import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.entity.User;

@Service
public class AdminMailSerivice {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    public void sendMailToUsers(AdminMailRequest request) {
        List<User> users = userRepository.findAll(); // ✅ Fetch all user emails

        for (User user : users) {
            String email = user.getEmail();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(request.getSubject());
            message.setText(request.getMessageBody());
            message.setFrom("nbasudev043@gmail.com");

            try {
                mailSender.send(message);
            } catch (MailException e) {
                System.err.println("Failed to send email to: " + email);
            }
        }
    }
}
