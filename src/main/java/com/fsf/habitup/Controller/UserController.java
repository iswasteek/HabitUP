package com.fsf.habitup.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/showauser/{email}")
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

    @PutMapping("/{email}/password")
    public ResponseEntity<String> updateUserPassword(@PathVariable String email, @RequestBody String newPassword) {
        boolean updated = userService.updateUserPassword(email, newPassword);
        if (!updated) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Password updated successfully.");
    }

    @PutMapping("/{email}/phoneno")
    public ResponseEntity<String> updateUserPhoneNo(@PathVariable String email, @RequestBody Long phoneNo) {
        boolean updated = userService.updateUserPhoneNo(email, phoneNo);
        if (!updated) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Phone Number updated successfully.");
    }

}
