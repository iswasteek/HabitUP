package com.fsf.habitup.Service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.fsf.habitup.DTO.AuthResponseDoctor;
import com.fsf.habitup.DTO.LoginRequest;
import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.Exception.ApiException;
import com.fsf.habitup.Repository.DoctorRepository;
import com.fsf.habitup.Security.JwtTokenProvider;
import com.fsf.habitup.entity.Doctor;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final OtpService otpService;

    public DoctorServiceImpl(AuthenticationManager authenticationManager, DoctorRepository doctorRepository,
            JwtTokenProvider jwtTokenProvider, OtpService otpService) {
        this.authenticationManager = authenticationManager;
        this.doctorRepository = doctorRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.otpService = otpService;
    }

    @Override
    public boolean deleteDoctor(Long doctorId) {
        if (doctorRepository.existsById(doctorId)) {
            doctorRepository.deleteById(doctorId);
            return true;
        }
        return false;
    }

    @Override
    public Doctor getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));

    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public boolean updateStatus(String email, AccountStatus accountStatus) {
        Doctor doctor = doctorRepository.findByEmailId(email);

        if (doctor == null) {
            return false;
        }

        doctor.setAccountStatus(accountStatus);
        doctorRepository.save(doctor);
        return true;
    }

    @Override
    public Doctor updateDoctor(Long doctorId, Doctor updateDoctor) {
        Doctor existingDoctor = doctorRepository.findById(doctorId).orElse(null);

        if (existingDoctor == null) {
            return null;
        }

        existingDoctor.setDoctorName(updateDoctor.getDoctorName());
        existingDoctor.setSpecialization(updateDoctor.getSpecialization());
        existingDoctor.setPhoneNo(updateDoctor.getPhoneNo());
        existingDoctor.setYearsOfExperience(updateDoctor.getYearsOfExperience());
        existingDoctor.setAvailabilitySchedule(updateDoctor.getAvailabilitySchedule());
        existingDoctor.setConsultationFee(updateDoctor.getConsultationFee());

        return doctorRepository.save(existingDoctor);
    }

    @Override
    public AuthResponseDoctor login(LoginRequest request) {
        Doctor doctor = doctorRepository.findByEmailId(request.getEmail());

        if (doctor == null) {
            throw new ApiException("User not found");
        }

        // Authenticate the doctor
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // Generate JWT Token
        String token = jwtTokenProvider.generateToken(request.getEmail());

        // Store token in the database
        doctor.setToken(token);
        doctorRepository.save(doctor);

        return new AuthResponseDoctor(doctor, token);
    }

    @Override
    public String Logout(String email) {
        Doctor doctor = doctorRepository.findByEmailId(email);

        if (doctor == null) {
            throw new ApiException("Doctor not found.");
        }

        // Clear the stored JWT token
        doctor.setToken(null);

        // Optional: Update status to INACTIVE
        doctor.setAccountStatus(AccountStatus.INACTIVE);

        // Save the updated doctor entity
        doctorRepository.save(doctor);

        return "Doctor logged out successfully.";
    }

}
