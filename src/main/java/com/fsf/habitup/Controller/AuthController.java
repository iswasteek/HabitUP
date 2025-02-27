package com.fsf.habitup.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsf.habitup.DTO.AuthResponse;
import com.fsf.habitup.DTO.LoginRequest;
import com.fsf.habitup.DTO.OtpVerificationReuest;
import com.fsf.habitup.DTO.RegisterRequest;
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
    public ResponseEntity<String> sendOtpToUserEmail(@RequestParam String email) {
        String response = userService.sendOtp(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-otp-and-register")
    public ResponseEntity<String> verifyOtpAndRegister(@RequestBody OtpVerificationReuest request1,
            @RequestBody RegisterRequest request2) {
        String response = userService.verifyOtpAndCreateUser(request1, request2);
        if (response.equals("Invalid or expired OTP")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Authenticate the user
        userService.authenticateUser(request);

        User user = new User();

        // Generate JWT token
        String token = jwtTokenProvider.generateToken(user.getEmail());

        // Create a response message
        String message = "Login successful for user: " + user.getName();

        return ResponseEntity.ok(new AuthResponse(token, message));
    }

}
