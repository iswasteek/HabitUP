package com.fsf.habitup.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsf.habitup.DTO.AdminRequest;
import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.Enums.UserType;
import com.fsf.habitup.Service.AdminServiceImpl;
import com.fsf.habitup.Service.DoctorServiceImpl;
import com.fsf.habitup.entity.Admin;
import com.fsf.habitup.entity.Doctor;
import com.fsf.habitup.entity.User;

@RestController
@RequestMapping("/habit/admin")
public class AdminController {

    private final AdminServiceImpl adminService;

    private final DoctorServiceImpl doctorService;

    public AdminController(AdminServiceImpl adminService, DoctorServiceImpl doctorService) {
        this.adminService = adminService;
        this.doctorService = doctorService;
    }

    @PostMapping("/add-admin")
    public ResponseEntity<Admin> addAdmin(@RequestBody AdminRequest request) {
        Admin admin = adminService.addAdmin(request); // Call the addAdmin method from the service
        return new ResponseEntity<>(admin, HttpStatus.CREATED); // Return the created admin with status 201

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

    @PostMapping("/send-verification-OTP")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        String response = adminService.sendOtp(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long adminId) {
        Admin admin = adminService.getAdminById(adminId);
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/all-admins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @DeleteMapping("/remove/{adminId}")
    // @PreAuthorize("hasAuthority('MANAGE_ADMINS')") // Ensures only authorized
    // users can remove admins
    public ResponseEntity<String> removeAdmin(@PathVariable Long adminId) {
        boolean isRemoved = adminService.removeAdmin(adminId);
        if (isRemoved) {
            return ResponseEntity.ok("Admin removed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found.");
        }
    }

    @PutMapping("/update-account-status/{userId}")
    public ResponseEntity<String> updateAccountStatus(@PathVariable Long userId,
            @RequestParam AccountStatus status) {
        boolean isUpdated = adminService.updateAccountStatus(userId, status);
        if (isUpdated) {
            return ResponseEntity.ok("User account status updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update account status.");
        }
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        boolean isDeleted = adminService.deleteUser(userId);
        if (isDeleted) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user.");
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserDetails(@PathVariable Long userId) {
        User user = adminService.getUserDetails(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users/assign-type")
    public ResponseEntity<String> assignUserType(@RequestParam Long userId, @RequestParam UserType userType) {
        boolean isAssigned = adminService.assignUserType(userId, userType);
        if (isAssigned) {
            return ResponseEntity.ok("UserType assigned successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to assign UserType.");
        }
    }

    @GetMapping("/users/type")
    public ResponseEntity<List<User>> getUsersByType(@RequestParam UserType userType) {
        List<User> users = adminService.getUsersByType(userType);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/has-permission")
    public ResponseEntity<Boolean> hasPermission(@RequestParam Long userId, @RequestParam UserType requiredUserType) {
        boolean hasPermission = adminService.hasPermission(userId, requiredUserType);
        return ResponseEntity.ok(hasPermission);
    }

    @GetMapping("/all-doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

}
