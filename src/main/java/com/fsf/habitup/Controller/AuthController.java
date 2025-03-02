package com.fsf.habitup.Controller;

import com.fsf.habitup.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsf.habitup.Security.JwtTokenProvider;
import com.fsf.habitup.Service.UserServiceImpl;
import com.fsf.habitup.entity.User;

@RestController
@RequestMapping("/habit/auth")
public class AuthController {

    @Autowired
    private final UserServiceImpl userService;

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserServiceImpl userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/send-OTP")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        String response = userService.sendOtp(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send-OTP-forgot-password")
    public ResponseEntity<String> sendOtpToUserEmail(@RequestParam String email) {
        String response = userService.SendOTPForForgotPassword(email);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/forgot-password-otp")
    public ResponseEntity<String> SendEmailForForgotPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest) {
        String response = userService.forgotPassword(forgetPasswordRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-otp-and-register")
    public ResponseEntity<String> verifyOtpAndRegister(@RequestBody OtpRegisterRequest request) {
        String response = userService.verifyOtpAndCreateUser(request);
        if (response.equals("Invalid or expired OTP")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Authenticate the user
        User user = userService.authenticateUser(request);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Generate JWT token
        String token = jwtTokenProvider.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token, String.valueOf(user.getUserId())));
    }

}
