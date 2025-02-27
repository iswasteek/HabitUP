package com.fsf.habitup.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

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
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("swastikproffesional@gmail.com");
        message.setSubject("Your OTP for Registration");
        message.setText("Your OTP is: " + otp + ". It is valid for 5 minutes.");

        mailSender.send(message);
    }
}
