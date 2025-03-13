package com.fsf.habitup.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsf.habitup.DTO.AuthResponseDoctor;
import com.fsf.habitup.DTO.LoginRequest;
import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.Exception.ApiException;
import com.fsf.habitup.Service.DoctorServiceImpl;
import com.fsf.habitup.entity.Doctor;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorServiceImpl doctorServiceImpl;

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long doctorId) {
        boolean deleted = doctorServiceImpl.deleteDoctor(doctorId);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found.");
        }

        return ResponseEntity.ok("Doctor deleted successfully.");
    }

    @GetMapping("show-a-doctor/{doctorId}")
    public ResponseEntity<Doctor> showDoctor(@PathVariable Long doctorId) {
        Doctor doctor = doctorServiceImpl.getDoctorById(doctorId);

        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(doctor);
    }

    @GetMapping("/show-all-doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorServiceImpl.getAllDoctors();

        if (doctors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(doctors);
    }

    @PostMapping("/update-doctor/{doctorId}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long doctorId, @RequestBody Doctor updateDoctor) {
        Doctor updatedDoctor = doctorServiceImpl.updateDoctor(doctorId, updateDoctor);

        if (updatedDoctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(updatedDoctor);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDoctor> doctorLogin(@RequestBody LoginRequest request) {

        AuthResponseDoctor authResponse = doctorServiceImpl.login(request);

        boolean statusUpdated = doctorServiceImpl.updateStatus(request.getEmail(), AccountStatus.ACTIVE);

        if (!statusUpdated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Return successful response with JWT token
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout/{email}")
    public ResponseEntity<String> doctorLogout(@PathVariable String email) {
        try {
            String response = doctorServiceImpl.Logout(email);
            return ResponseEntity.ok(response);
        } catch (ApiException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

}
