package com.fsf.habitup.Service;

import com.fsf.habitup.DTO.LoginRequest;
import com.fsf.habitup.DTO.RegisterRequest;
import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {

    @Autowired
    UserRepository userRepository = null;


    public String registerUser(RegisterRequest request);

    public String authenticateUser(LoginRequest request);

    public default User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
