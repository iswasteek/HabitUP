package com.fsf.habitup.DTO;

import com.fsf.habitup.entity.Admin;

public class AuthResponseAdmin {
    private String token;
    private AdminResponse admin;

    public AuthResponseAdmin(String token, Admin admin) {
        this.token = token;
        this.admin = AdminResponse.fromEntity(admin);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AdminResponse getAdmin() {
        return admin;
    }

    public void setAdmin(AdminResponse admin) {
        this.admin = admin;
    }
}
