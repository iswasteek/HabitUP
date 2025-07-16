package com.fsf.habitup.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.fsf.habitup.Enums.DocumentStatus;
import com.fsf.habitup.Enums.PermissionType;
import com.fsf.habitup.Enums.UserType;
import com.fsf.habitup.Service.AdminServiceImpl;
import com.fsf.habitup.Service.DoctorServiceImpl;
import com.fsf.habitup.Service.DocumentService;
import com.fsf.habitup.entity.Admin;
import com.fsf.habitup.entity.Doctor;
import com.fsf.habitup.entity.Documents;
import com.fsf.habitup.entity.Permission;
import com.fsf.habitup.entity.User;

@RestController
@RequestMapping("/habit/admin")

public class AdminController {

    private final AdminServiceImpl adminService;

    @Autowired
    private DocumentService documentService;

    private final DoctorServiceImpl doctorService;

    public AdminController(AdminServiceImpl adminService, DoctorServiceImpl doctorService) {
        this.adminService = adminService;
        this.doctorService = doctorService;
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGE_ADMIN')")
    @PostMapping("/add-admin")
    public ResponseEntity<Admin> addAdmin(@RequestBody AdminRequest request) {
        Admin admin = adminService.addAdmin(request); // Call the addAdmin method from the service
        return new ResponseEntity<>(admin, HttpStatus.CREATED); // Return the created admin with status 201

    }

    @PreAuthorize("hasAuthority('ROLE_MANAGE_DOCTORS')")
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

    @PreAuthorize("hasAuthority('ROLE_SEND_NOTIFICATIONS')")
    @PostMapping("/send-verification-OTP")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        String response = adminService.sendOtp(email);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('ROLE_VIEW_ADMINS')")
    @GetMapping("/{adminId}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long adminId) {
        Admin admin = adminService.getAdminById(adminId);
        return ResponseEntity.ok(admin);
    }

    @PreAuthorize("hasAuthority('ROLE_VIEW_ADMINS')")
    @GetMapping("/all-admins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @DeleteMapping("/remove/{adminId}")
    @PreAuthorize("hasAuthority('ROLE_MANAGE_ADMINS')")
    // Ensures only authorized
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
    @PreAuthorize("hasAuthority('ROLE_ACTIVATE_USERS')")
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
    @PreAuthorize("hasAuthority('ROLE_LOCK_UNLOCK_USERS')")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        boolean isDeleted = adminService.deleteUser(userId);
        if (isDeleted) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user.");
        }
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('ROLE_VIEW_USERS')")
    public ResponseEntity<User> getUserDetails(@PathVariable Long userId) {
        User user = adminService.getUserDetails(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_VIEW_USERS')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/assign-type")
    @PreAuthorize("hasAuthority('ROLE_MANAGE_USERS')")
    public ResponseEntity<String> assignUserType(@RequestParam Long userId, @RequestParam UserType userType) {
        boolean isAssigned = adminService.assignUserType(userId, userType);
        if (isAssigned) {
            return ResponseEntity.ok("UserType assigned successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to assign UserType.");
        }
    }

    @GetMapping("/users/type")
    @PreAuthorize("hasAuthority('ROLE_VIEW_USERS')")
    public ResponseEntity<List<User>> getUsersByType(@RequestParam UserType userType) {
        List<User> users = adminService.getUsersByType(userType);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/has-permission")
    @PreAuthorize("hasAuthority('ROLE_MANAGE_USERS')")
    public ResponseEntity<Boolean> hasPermission(@RequestParam Long userId, @RequestParam UserType requiredUserType) {
        boolean hasPermission = adminService.hasPermission(userId, requiredUserType);
        return ResponseEntity.ok(hasPermission);
    }

    @GetMapping("/all-doctors")
    @PreAuthorize("hasAuthority('ROLE_VIEW_DOCTORS')")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @PutMapping("/reject-doctor/{doctorId}")
    @PreAuthorize("hasAuthority('ROLE_MANAGE_DOCTORS')")
    public ResponseEntity<String> rejectDoctor(@PathVariable Long doctorId) {
        boolean isRejected = adminService.rejectDoctor(doctorId);

        if (isRejected) {
            return ResponseEntity.ok("Doctor has been rejected successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to reject the doctor.");
        }
    }

    @GetMapping("/pending-documents")
    @PreAuthorize("hasAuthority('ROLE_VIEW_DOCUMENTS')")
    public ResponseEntity<List<Documents>> getPendingDocuments() {
        List<Documents> pendingDocuments = adminService.getPendingDocuments();

        if (pendingDocuments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(pendingDocuments);
    }

    @PutMapping("/update-document-status/{documentId}")
    @PreAuthorize("hasAuthority('ROLE_MANAGE_DOCUMENTS')")
    public ResponseEntity<String> updateDocumentStatus(
            @PathVariable Long documentId,
            @RequestParam DocumentStatus status) {

        boolean isUpdated = adminService.updateDocumentStatus(documentId, status);

        if (isUpdated) {
            return ResponseEntity.ok("Document status updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to update document status.");
        }
    }

    @GetMapping("/user/permission/{userId}")
    @PreAuthorize("hasAuthority('ROLE_VIEW_PERMISSIONS')")
    public ResponseEntity<List<Permission>> getPermissionsForUser(@PathVariable Long userId) {
        List<Permission> permissions = adminService.getPermissionsForUser(userId);
        return ResponseEntity.ok(permissions);
    }

    // Get all permissions for a doctor
    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAuthority('ROLE_VIEW_PERMISSIONS')")
    public ResponseEntity<List<Permission>> getPermissionsForDoctor(@PathVariable Long doctorId) {
        List<Permission> permissions = adminService.getPermissionsForDoctor(doctorId);
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/user/{userId}/check/{permissionName}")
    @PreAuthorize("hasAuthority('ROLE_VIEW_PERMISSIONS')")
    public ResponseEntity<Boolean> checkUserPermission(
            @PathVariable Long userId,
            @PathVariable PermissionType permissionName) {

        boolean hasPermission = adminService.checkUserPermission(userId, permissionName);
        return ResponseEntity.ok(hasPermission);
    }

    // Check if a doctor has a specific permission
    @GetMapping("/doctor/{doctorId}/check/{permissionName}")
    @PreAuthorize("hasAuthority('ROLE_VIEW_PERMISSIONS')")
    public ResponseEntity<Boolean> checkDoctorPermission(
            @PathVariable Long doctorId,
            @PathVariable PermissionType permissionName) {

        boolean hasPermission = adminService.checkDoctorPermission(doctorId, permissionName);
        return ResponseEntity.ok(hasPermission);
    }

    @GetMapping("/{adminId}/has-permission/{permissionName}")
    @PreAuthorize("hasAuthority('ROLE_VIEW_PERMISSIONS')")
    public ResponseEntity<Boolean> hasPermission(
            @PathVariable Long adminId,
            @PathVariable String permissionName) {

        boolean hasPermission = adminService.hasPermission(adminId, permissionName);
        return ResponseEntity.ok(hasPermission);
    }

}
