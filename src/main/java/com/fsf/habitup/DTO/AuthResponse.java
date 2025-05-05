package com.fsf.habitup.DTO;

import com.fsf.habitup.entity.User;

public class AuthResponse {
    private String token;
    private UserResponseDTO user;

    public AuthResponse(String token, User user) {
        this.token = token;
        this.user = new UserResponseDTO(user);
    }

    public String getToken() {
        return token;
    }

    public UserResponseDTO getUser() {
        return user;
    }
}