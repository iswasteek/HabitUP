package com.fsf.habitup.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsf.habitup.DTO.AuthResponse;
import com.fsf.habitup.DTO.ForgetPasswordRequest;
import com.fsf.habitup.DTO.LoginRequest;
import com.fsf.habitup.DTO.OtpRegisterRequest;
import com.fsf.habitup.Exception.ApiException;
import com.fsf.habitup.Service.UserServiceImpl;

@RestController
@RequestMapping("/habit/auth")
public class AuthController {

    @Autowired
    private final UserServiceImpl userService;

    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
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
        AuthResponse authResponse = userService.authenticateUser(request);
        return ResponseEntity.ok(authResponse);

    }

    @PostMapping("/logout/{email}")
    public ResponseEntity<String> logout(@PathVariable String email) {
        try {
            // Call the logout method from the user service
            String response = userService.logout(email);
            return ResponseEntity.ok(response);
        } catch (ApiException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }

    }

}
