package com.fsf.habitup.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fsf.habitup.DTO.LoginRequest;
import com.fsf.habitup.DTO.RegisterRequest;
import com.fsf.habitup.Exception.ApiException;
import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.entity.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public User registerUser(RegisterRequest request) {
        User newUser = userRepository.findByEmail(request.getEmail());

        if (newUser != null && newUser.getEmail().equals(request.getEmail())) { // Check for duplicate email
            throw new ApiException("This user is already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setUser_id(null);
        user.setJoinDate(null);
        user.setDob(request.getDateOfBirth());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        System.out.println("registered successfully");

        return user;

    }
    
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User authenticateUser(LoginRequest request) {
        // Find the user by username
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new ApiException("User not found");
        }

        // Compare the plaintext password from the request with the hashed password stored in the database
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!passwordMatches) {
            throw new ApiException("Invalid username or password");
        }

        // Return the user if authentication is successful
        return user;
    }
}
