package com.fsf.habitup.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsf.habitup.DTO.LogoutResponse;
import com.fsf.habitup.Service.DoctorServiceImpl;
import com.fsf.habitup.entity.Doctor;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorServiceImpl doctorServiceImpl;

    @PreAuthorize("hasAuthority('MANAGE_DOCTORS')")
    @DeleteMapping("/{doctorId}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long doctorId) {
        boolean deleted = doctorServiceImpl.deleteDoctor(doctorId);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found.");
        }

        return ResponseEntity.ok("Doctor deleted successfully.");
    }

    @PreAuthorize("hasAuthority('VIEW_DOCTORS')")
    @GetMapping("show-a-doctor/{doctorId}")
    public ResponseEntity<Doctor> showDoctor(@PathVariable Long doctorId) {
        Doctor doctor = doctorServiceImpl.getDoctorById(doctorId);

        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(doctor);
    }

    @PreAuthorize("hasAuthority('VIEW_DOCTORS')")
    @GetMapping("/show-all-doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorServiceImpl.getAllDoctors();

        if (doctors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(doctors);
    }

    @PreAuthorize("hasAuthority('MANAGE_DOCTORS')")
    @PutMapping("/update-doctor/{doctorId}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long doctorId, @RequestBody Doctor updateDoctor) {
        Doctor updatedDoctor = doctorServiceImpl.updateDoctor(doctorId, updateDoctor);

        if (updatedDoctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(updatedDoctor);
    }

    @PostMapping("/doctor/logout")
    @PreAuthorize("isAuthenticated()") // Requires authentication before logout
    public ResponseEntity<LogoutResponse> logout(@RequestParam String email, HttpServletResponse response) {
        LogoutResponse logoutResponse = doctorServiceImpl.Logout(email, response);
        return ResponseEntity.ok(logoutResponse);
    }

}
