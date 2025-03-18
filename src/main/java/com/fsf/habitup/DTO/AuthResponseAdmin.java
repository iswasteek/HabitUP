package com.fsf.habitup.DTO;

import com.fsf.habitup.entity.Admin;

public class AuthResponseAdmin {
    private String token;
    private Admin admin;

    public AuthResponseAdmin(String token, Admin admin) {
        this.token = token;
        this.admin = admin;
    }

    /**
     * @return String return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return Admin return the admin
     */
    public Admin getAdmin() {
        return admin;
    }

    /**
     * @param admin the admin to set
     */
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

}
