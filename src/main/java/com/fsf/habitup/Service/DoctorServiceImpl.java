package com.fsf.habitup.Service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.entity.Doctor;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Override
    public boolean deleteDoctor(Long doctorId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Doctor getDoctorById(Long doctorId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Doctor> getAllDoctors() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean updateDoctorName(Long doctorId, String newName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean updatePhoneNo(Long doctorId, Long newPhoneNo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean updateGender(Long doctorId, String newGender) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean updateSpecialization(Long doctorId, String newSpecialization) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean updateYearsOfExperience(Long doctorId, int newYearsOfExperience) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean updateAvilabilitySchedule(Long doctorId, Date newAvailabilitySchedule) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean updateConsultationFee(Long doctorId, int newConsultationFee) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean updateRatings(Long doctorId, float newRatings) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean updateStatus(Long doctorId, AccountStatus accountStatus) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
