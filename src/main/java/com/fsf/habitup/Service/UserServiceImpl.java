package com.fsf.habitup.Service;

import java.util.Date;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fsf.habitup.DTO.LoginRequest;
import com.fsf.habitup.DTO.RegisterRequest;
import com.fsf.habitup.Exception.ApiException;
import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.entity.User;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(AuthenticationManager auth, PasswordEncoder passwordEncoder,
            UserRepository userRepo) {
        this.authenticationManager = auth;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepo;

    }

    @Override
    public String registerUser(RegisterRequest request) {
        User newUser = userRepository.findByEmail(request.getEmail());

        if (newUser != null && newUser.getEmail().equals(request.getEmail())) {
            throw new ApiException("This user is already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setJoinDate(null);
        user.setDob(request.getDateOfBirth());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNo(request.getPhoneNumber());

        userRepository.save(user);

        return "Registration successful! Check your email to verify.";
    }

    @Override
    public String authenticateUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new ApiException("User not found");
        }
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        return "Authentication successful!";
    }

    @Override
    public User updateUser(String email, User updateUser) {

        User existingUser = userRepository.findByEmail(email);

        if (existingUser == null) {
            throw new ApiException("user not found");
        }

        existingUser.setName(updateUser.getName());
        existingUser.setEmail(updateUser.getEmail());
        existingUser.setPassword(updateUser.getPassword());
        existingUser.setDob(updateUser.getDob());
        existingUser.setPhoneNo(updateUser.getPhoneNumber());

        String message = "updated successfully!!";
        System.out.println(message);
        return existingUser;
    }

    @Override
    public User getUserByEmail(String email) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            throw new ApiException("user is not found!!");
        }
        return existingUser;
    }

    @Override
    public boolean deleteUser(String email) {
        if (userRepository.findByEmail(email) == null) {
            return false;
        }
        userRepository.deleteByEmail(email);
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean updateUserPassword(String email, String newPassword) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            return false;
        }
        existingUser.setPassword(newPassword);
        userRepository.save(existingUser);
        return true;
    }

    @Override
    public boolean updateUserPhoneNo(String email, Long newPhoneNo) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            return false;
        }
        existingUser.setPhoneNo(newPhoneNo);
        userRepository.save(existingUser);
        return true;
    }

    @Override
    public boolean updateUserDob(String email, Date newDob) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            return false;
        }
        existingUser.setDob(newDob);
        userRepository.save(existingUser);
        return true;
    }

    @Override
    public boolean updateUserName(String email, String Name) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            return false;
        }
        existingUser.setName(Name);
        userRepository.save(existingUser);
        return true;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

}
