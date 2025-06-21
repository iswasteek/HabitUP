package com.fsf.habitup.Controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsf.habitup.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fsf.habitup.DTO.LogoutResponse;
import com.fsf.habitup.DTO.UpdateUserDTO;
import com.fsf.habitup.DTO.UserResponseDTO;
import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.Enums.Gender;
import com.fsf.habitup.Service.DocumentService;
import com.fsf.habitup.Service.UserServiceImpl;
import com.fsf.habitup.entity.User;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import static com.fsf.habitup.Service.UserService.userRepository;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private DocumentService documentService;
    @Autowired
    private final UserServiceImpl userService;
    @Autowired
    private final UserRepository userRepository;

    public UserController(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam("type") String type) {

        try {
            documentService.uploadDocument(userId, file, type);
            return ResponseEntity.ok("Document uploaded successfully and marked as PENDING.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload failed: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUserProfileByEmail(
            @RequestParam String email,
            @RequestParam("userDTO") String userDTOString, // JSON String of UpdateUserDTO
            @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto) {

        ObjectMapper objectMapper = new ObjectMapper();
        UpdateUserDTO userDTOObject;

        try {
            userDTOObject = objectMapper.readValue(userDTOString, UpdateUserDTO.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error parsing userDTO: " + e.getMessage());
        }

        byte[] imageBytes = null;

        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            try {
                imageBytes = profilePhoto.getBytes();
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error processing profile photo: " + e.getMessage());
            }
        }

        try {
            User updatedUser = userService.updateUser(email, userDTOObject, imageBytes);
            return ResponseEntity.ok(new UserResponseDTO(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating user: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('VIEW_USERS')")
    @GetMapping("/show-a-user/{email}")
    public ResponseEntity<UserResponseDTO> showUser(@PathVariable String email) {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new UserResponseDTO(user));
    }

    @PreAuthorize("hasAuthority('VIEW_PROFILE')")
    @GetMapping("/profile-photo/{email}")
    public ResponseEntity<?> getUserProfilePhoto(@PathVariable String email) {
        User user = userRepository.findByEmail(email);
        if (user == null || user.getProfilePhoto() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or profile photo not found");
        }

        String base64Image = user.getProfilePhoto();

        // Optional: Add data URI prefix if not already present
        if (!base64Image.startsWith("data:image")) {
            base64Image = "data:image/jpeg;base64," + base64Image; // or image/png
        }

        return ResponseEntity.ok(base64Image);
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @Transactional
    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {

        boolean deleted = userService.deleteUser(email);

        if (!deleted) {
            return ResponseEntity.notFound().build(); // 404 if user not found
        }

        return ResponseEntity.ok("User deleted successfully.");
    }

    @PreAuthorize("hasAuthority('RESET_USER_PASSWORDS')")
    @PostMapping("/{email}/send-mail")
    public ResponseEntity<String> sendPasswordResetEmail(@PathVariable String email) {
        boolean emailSent = userService.sendPasswordResetToken(email);
        if (!emailSent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or email not sent.");
        }
        return ResponseEntity.ok("Password reset email sent successfully.");
    }

    @PreAuthorize("hasAuthority('RESET_USER_PASSWORDS')")
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody String newPassword) {
        boolean isReset = userService.resetPassword(token, newPassword);
        if (!isReset) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
        }
        return ResponseEntity.ok("Password has been reset successfully.");
    }

    @PreAuthorize("hasAuthority('MANAGE_SUBSCRIPTIONS')")
    @PutMapping("/update-subscription/{userId}")
    public ResponseEntity<String> updateSubscriptionType(@PathVariable Long userId, @RequestParam boolean isPaid) {
        boolean updated = userService.updateSubscriptionType(userId, null, isPaid);

        return updated
                ? ResponseEntity.ok("Subscription updated successfully.")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No change needed or user not found.");

    }

    @PreAuthorize("hasAuthority('ACTIVATE_USERS')")
    @PutMapping("/update-account-status/{userId}")
    public ResponseEntity<String> updateAccountStatus(@PathVariable Long userId,
            @RequestParam AccountStatus accountStatus) {
        boolean updated = userService.updateAccountStatus(userId, accountStatus);

        return updated ? ResponseEntity.ok("Account status updated successfully.")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No change needed or user not found.");

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/user/logout")
    public ResponseEntity<LogoutResponse> logout(@RequestParam String email, HttpServletResponse response) {
        LogoutResponse logoutResponse = userService.logout(email, response);
        return ResponseEntity.ok(logoutResponse);
    }
}
