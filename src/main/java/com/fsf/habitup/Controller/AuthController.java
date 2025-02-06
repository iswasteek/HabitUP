package com.fsf.habitup.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsf.habitup.DTO.AuthResponse;
import com.fsf.habitup.DTO.LoginRequest;
import com.fsf.habitup.DTO.RegisterRequest;
import com.fsf.habitup.Security.JwtTokenProvider;
import com.fsf.habitup.Service.UserServiceImpl;
import com.fsf.habitup.entity.User;

@RestController
@RequestMapping("/habit/auth")
public class AuthController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider; // Inject JWT utility

   
    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }
    
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Authenticate the user
        User user = userService.authenticateUser(request);

        // Generate JWT token
        String token = jwtTokenProvider.generateToken(user.getEmail());

        // Create a response message
        String message = "Login successful for user: " + user.getName();

        return ResponseEntity.ok(new AuthResponse(token, message));
    }
}
