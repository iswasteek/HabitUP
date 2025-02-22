package com.fsf.habitup.Service;

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

    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
//        user.setJoinDate(null);
        user.setDob(request.getDateOfBirth());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

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

}
