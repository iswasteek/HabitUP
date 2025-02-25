package com.fsf.habitup.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.Repository.DoctorRepository;
import com.fsf.habitup.entity.Doctor;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
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
    public boolean updateStatus(Long doctorId, AccountStatus accountStatus) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

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

}
