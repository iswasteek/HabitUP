package com.fsf.habitup.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    @Autowired
    private UserRepository userRepository;

    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    private final JavaMailSender mailSender;

    public OtpService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void generateAndSendOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000); // Generate 6-digit OTP
        otpStorage.put(email, otp);

        sendOtpEmail(email, otp);
    }

    public boolean validateOtp(String email, String enteredOtp) {
        String storedOtp = otpStorage.get(email);
        return storedOtp != null && storedOtp.equals(enteredOtp);
    }

    private void sendOtpEmail(String email, String otp) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setFrom("swastikproffesional@gmail.com");
            helper.setSubject("Message From HabitUP");

            // HTML Email Template
            String emailContent = "<!DOCTYPE html>" +
                    "<html><head><meta charset='UTF-8'>" +
                    "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                    "<title>Verification Code</title>" +
                    "<style>" +
                    "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }" +
                    ".container { max-width: 500px; margin: 30px auto; background: #ffffff; padding: 20px; border-radius: 8px;" +
                    "box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); }" +
                    ".header { text-align: center; font-size: 22px; font-weight: bold; color: #333; padding-bottom: 10px;" +
                    "border-bottom: 2px solid #eaeaea; }" +
                    ".content { margin-top: 20px; font-size: 16px; color: #555; line-height: 1.6; }" +
                    ".otp { font-size: 24px; font-weight: bold; color: #1a73e8; text-align: center; padding: 10px;" +
                    "border-radius: 5px; background: #f1f3f4; margin: 15px 0; }" +
                    ".footer { margin-top: 20px; font-size: 14px; color: #777; text-align: center; }" +
                    "</style></head><body>" +
                    "<div class='container'>" +
                    "<div class='header'>Your Verification Code</div>" +
                    "<div class='content'>" +
                    "<p>Hi <b>" + user.getName() + "</b>,</p>" +
                    "<p>Your one-time verification code is:</p>" +
                    "<div class='otp'>" + otp + "</div>" +
                    "<p>This code is valid for <b>5 minutes</b>. Please do not share it with anyone.</p>" +
                    "<p>If you did not request this verification, you can ignore this email.</p>" +
                    "</div>" +
                    "<div class='footer'><p>Thanks, <br><b>Team HabitUp</b></p></div>" +
                    "</div></body></html>";

            helper.setText(emailContent, true);

            mailSender.send(message);
            System.out.println("OTP email sent successfully!");
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
