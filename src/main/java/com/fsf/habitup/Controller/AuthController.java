package com.fsf.habitup.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsf.habitup.DTO.AdminMailRequest;
import com.fsf.habitup.DTO.AuthResponse;
import com.fsf.habitup.DTO.AuthResponseAdmin;
import com.fsf.habitup.DTO.AuthResponseDoctor;
import com.fsf.habitup.DTO.ForgetPasswordRequest;
import com.fsf.habitup.DTO.LoginRequest;
import com.fsf.habitup.DTO.OtpRegisterRequest;
import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.Service.AdminMailSerivice;
import com.fsf.habitup.Service.AdminServiceImpl;
import com.fsf.habitup.Service.DoctorServiceImpl;
import com.fsf.habitup.Service.UserServiceImpl;
import com.fsf.habitup.Service.UserToAdminMailService;

@RestController
@RequestMapping("/habit/auth")
public class AuthController {

    @Autowired
    private final UserServiceImpl userService;

    private final DoctorServiceImpl doctorService;

    private final AdminServiceImpl adminService;

    @Autowired
    private AdminMailSerivice adminMailService;

    @Autowired
    private UserToAdminMailService mailService;

    public AuthController(UserServiceImpl userService, DoctorServiceImpl doctorService, AdminServiceImpl adminService) {
        this.userService = userService;
        this.doctorService = doctorService;
        this.adminService = adminService;

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
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse authResponse = userService.authenticateUser(request);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/doctor/login")
    public ResponseEntity<AuthResponseDoctor> doctorLogin(@RequestBody LoginRequest request) {

        AuthResponseDoctor authResponse = doctorService.login(request);

        boolean statusUpdated = doctorService.updateStatus(request.getEmail(), AccountStatus.ACTIVE);

        if (!statusUpdated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Return successful response with JWT token
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<AuthResponseAdmin> adminLogin(@RequestBody LoginRequest request) {
        AuthResponseAdmin response = adminService.AdminLogin(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/support/sendmailtousers")
    public ResponseEntity<String> sendSupportMail(@RequestBody AdminMailRequest request) {
        adminMailService.sendMailToUsers(request);
        return ResponseEntity.ok("Emails sent successfully!");
    }

    // @PostMapping("/support/sendmailtohabitup")
    // public ResponseEntity<String> sendMail(@RequestBody UserMailRequest request)
    // {
    // mailService.sendMailToAdmin(request);
    // return ResponseEntity.ok("Mail sent to admin successfully.");
    // }

}
