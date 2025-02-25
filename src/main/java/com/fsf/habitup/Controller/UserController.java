package com.fsf.habitup.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.Service.UserServiceImpl;
import com.fsf.habitup.entity.User;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PutMapping("/updateuser/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User updatedUser) {

        User savedUser = userService.updateUser(email, updatedUser);

        if (savedUser == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/show-a-user/{email}")
    public ResponseEntity<User> showUser(@PathVariable String email) {
        User user = userService.getUserByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);

    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        boolean deleted = userService.deleteUser(email);

        if (!deleted) {
            return ResponseEntity.notFound().build(); // Return 404 if user is not found
        }

        return ResponseEntity.ok("User deleted successfully.");
    }

    @GetMapping("/showallusers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{email}/send-mail")
    public ResponseEntity<String> sendPasswordResetEmail(@PathVariable String email) {
        boolean emailSent = userService.sendPasswordResetToken(email);
        if (!emailSent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or email not sent.");
        }
        return ResponseEntity.ok("Password reset email sent successfully.");
    }

    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody String newPassword) {
        boolean isReset = userService.resetPassword(token, newPassword);
        if (!isReset) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
        }
        return ResponseEntity.ok("Password has been reset successfully.");
    }

    @PutMapping("/update-user-type/{userId}")
    public ResponseEntity<String> updateUserType(@PathVariable Long userId) {
        boolean updated = userService.updateUserType(userId, null);
        return updated ? ResponseEntity.ok("User type updated successfully.")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User type update failed.");
    }

    @PutMapping("/update-subscription/{userId}")
    public ResponseEntity<String> updateSubscriptionType(@PathVariable Long userId, @RequestParam boolean isPaid) {
        boolean updated = userService.updateSubscriptionType(userId, null, isPaid);
        return updated ? ResponseEntity.ok("Subscription updated successfully.")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No change needed or user not found.");
    }

    @PutMapping("/update-account-status/{userId}")
    public ResponseEntity<String> updateAccountStatus(@PathVariable Long userId, @RequestBody String accountStatus) {
        try {
            AccountStatus status = AccountStatus.valueOf(accountStatus.toUpperCase());
            boolean updated = userService.updateAccountStatus(userId, status);
            return updated ? ResponseEntity.ok("Account status updated successfully.")
                    : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid status or no change needed.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid account status provided.");
        }
    }

}
