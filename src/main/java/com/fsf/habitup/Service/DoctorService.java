package com.fsf.habitup.Service;

import java.util.List;

import com.fsf.habitup.DTO.AuthResponseDoctor;
import com.fsf.habitup.DTO.LoginRequest;
import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.entity.Doctor;

public interface DoctorService {

    public boolean deleteDoctor(Long doctorId);

    public Doctor getDoctorById(Long doctorId);

    public List<Doctor> getAllDoctors();

    public Doctor updateDoctor(Long doctorId, Doctor updateDoctor);

    public boolean updateStatus(String email, AccountStatus accountStatus);

    public AuthResponseDoctor login(LoginRequest request);

    public String Logout(String email);

}
