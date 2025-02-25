package com.fsf.habitup.Service;

import java.util.Date;
import java.util.List;

import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.entity.Doctor;

public interface DoctorService {

    public boolean deleteDoctor(Long doctorId);

    public Doctor getDoctorById(Long doctorId);

    public List<Doctor> getAllDoctors();

    public boolean updateDoctorName(Long doctorId, String newName);

    public boolean updatePhoneNo(Long doctorId, Long newPhoneNo);

    public boolean updateGender(Long doctorId, String newGender);

    public boolean updateSpecialization(Long doctorId, String newSpecialization);

    public boolean updateYearsOfExperience(Long doctorId, int newYearsOfExperience);

    public boolean updateAvilabilitySchedule(Long doctorId, Date newAvailabilitySchedule);

    public boolean updateConsultationFee(Long doctorId, int newConsultationFee);

    public boolean updateRatings(Long doctorId, float newRatings);

    public boolean updateStatus(Long doctorId, AccountStatus accountStatus);

}
