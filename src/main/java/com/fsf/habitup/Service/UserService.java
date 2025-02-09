package com.fsf.habitup.Service;

import com.fsf.habitup.DTO.LoginRequest;
import com.fsf.habitup.DTO.RegisterRequest;

public interface UserService {
    public String registerUser(RegisterRequest request);

    public String authenticateUser(LoginRequest request);
}
