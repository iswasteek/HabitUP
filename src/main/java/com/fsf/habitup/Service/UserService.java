package com.fsf.habitup.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.fsf.habitup.DTO.LoginRequest;
import com.fsf.habitup.DTO.RegisterRequest;
import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.entity.User;

public interface UserService {

	public User registerUser(RegisterRequest request);

    public User authenticateUser(LoginRequest request); // Add this method

    @Autowired
    UserRepository userRepository = null;

    public default User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
