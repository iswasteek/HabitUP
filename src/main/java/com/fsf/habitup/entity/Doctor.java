package com.fsf.habitup.entity;

import java.util.Date;

import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.Enums.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctorId", nullable = false, unique = true)
    private Long doctorId;

    @Column(name = "doctorName", nullable = false, unique = false)
    private String doctorName;

    @Column(name = "email", nullable = false, unique = true)
    private String emailId;

    @Column(name = "phoneNo", nullable = false, unique = true)
    private Long phoneNo;

    @Column(name = "gender", nullable = false, unique = false)
    private String gender;

    @Column(name = "specialization", nullable = false, unique = false)
    private String specialization;

    @Column(name = "yearsOfExperience", nullable = false, unique = false)
    private byte yearsOfExperience;

    @Column(name = "availabilitySchedule", nullable = false, unique = false)
    private Date availabilitySchedule;

    @Column(name = "consultationFee", nullable = false, unique = false)
    private int consultationFee;

    @Column(name = "ratings", nullable = false, unique = false)
    private float ratings;

    @Column(name = "status", nullable = false, unique = false)
    private String status;

    @Column(name = "userType", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "AccountStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Date getAvailabilitySchedule() {
        return availabilitySchedule;
    }

    public void setAvailabilitySchedule(Date availabilitySchedule) {
        this.availabilitySchedule = availabilitySchedule;
    }

    public int getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(int consultationFee) {
        this.consultationFee = consultationFee;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctor_Id(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(byte yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}