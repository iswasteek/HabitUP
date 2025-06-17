package com.fsf.habitup.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fsf.habitup.DTO.UserMailRequest;

@Service
public class UserToAdminMailService {
    @Autowired
    private JavaMailSender mailSender;

    private static final String ADMIN_EMAIL = "mbasudev022@gmail.com";

    public void sendMailToAdmin(UserMailRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(ADMIN_EMAIL);
        message.setSubject("User Message: " + request.getSubject());
        message.setText("From: " + request.getUserEmail() + "\n\nMessage:\n" + request.getMessageBody());
        message.setFrom(request.getUserEmail());

        mailSender.send(message);
    }
}
