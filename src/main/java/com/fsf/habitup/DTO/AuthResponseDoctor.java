package com.fsf.habitup.DTO;

import com.fsf.habitup.entity.Doctor;

public class AuthResponseDoctor {
    private String token;
    private Doctor doctor;

    public AuthResponseDoctor(Doctor doctor, String token) {
        this.doctor = doctor;
        this.token = token;
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
     * @return Doctor return the doctor
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * @param doctor the doctor to set
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

}
