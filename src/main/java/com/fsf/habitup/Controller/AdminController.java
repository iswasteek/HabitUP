package com.fsf.habitup.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsf.habitup.DTO.AdminRequest;
import com.fsf.habitup.Exception.InvalidOtpException;
import com.fsf.habitup.Service.AdminServiceImpl;
import com.fsf.habitup.entity.Admin;

@RestController
@RequestMapping("/habit/admin")
public class AdminController {

    private final AdminServiceImpl adminService;

    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/add-admin")
    public ResponseEntity<Admin> addAdmin(@RequestBody AdminRequest request) {
        try {
            Admin admin = adminService.addAdmin(request); // Call the addAdmin method from the service
            return new ResponseEntity<>(admin, HttpStatus.CREATED); // Return the created admin with status 201
        } catch (InvalidOtpException ex) {
            // Catch the invalid OTP exception and return a 400 Bad Request
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            // General exception handling to return 500 Internal Server Error
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userId}/verify-as-doctor")
    public ResponseEntity<String> verifyUserForDoctor(@PathVariable Long userId) {
        try {
            // Call the service to verify and convert the user to a doctor
            boolean isVerified = adminService.verifyUserForDoctor(userId);

            // If successful, return a success message
            if (isVerified) {
                return new ResponseEntity<>("User successfully verified as doctor.", HttpStatus.OK);
            }
            // In case something unexpected happens, you can handle here
            return new ResponseEntity<>("User verification failed.", HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (RuntimeException ex) {
            // Catch the runtime exceptions thrown in the service and send the appropriate
            // response
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
