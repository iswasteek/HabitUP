package com.fsf.habitup.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.Service.ProfileServiceImpl;
import com.fsf.habitup.entity.User;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private final ProfileServiceImpl profileServiceImpl;

    public ProfileController(ProfileServiceImpl profileServiceImpl) {
        this.profileServiceImpl = profileServiceImpl;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
        User user = profileServiceImpl.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = profileServiceImpl.getAllUsers();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @PutMapping("/update-photo/{userId}")
    public ResponseEntity<String> updateProfilePhoto(@PathVariable Long userId, @RequestBody String newProfilePhoto) {
        boolean updated = profileServiceImpl.updateProfilePhoto(userId, newProfilePhoto);
        return updated ? ResponseEntity.ok("Profile photo updated successfully.")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    @PutMapping("/update-account-status/{userId}")
    public ResponseEntity<String> updateAccountStatus(@PathVariable Long userId, @RequestBody String accountStatus) {
        try {
            AccountStatus status = AccountStatus.valueOf(accountStatus.toUpperCase());
            boolean updated = profileServiceImpl.updateAccountStatus(userId, status);
            return updated ? ResponseEntity.ok("Account status updated successfully.")
                    : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid status or no change needed.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid account status provided.");
        }
    }

    @PutMapping("/update-subscription/{userId}")
    public ResponseEntity<String> updateSubscriptionType(@PathVariable Long userId, @RequestParam boolean isPaid) {
        boolean updated = profileServiceImpl.updateSubscriptionType(userId, null, isPaid);
        return updated ? ResponseEntity.ok("Subscription updated successfully.")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No change needed or user not found.");
    }

    @PutMapping("/update-user-type/{userId}")
    public ResponseEntity<String> updateUserType(@PathVariable Long userId) {
        boolean updated = profileServiceImpl.updateUserType(userId, null);
        return updated ? ResponseEntity.ok("User type updated successfully.")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User type update failed.");
    }
}
