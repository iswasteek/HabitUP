package com.fsf.habitup.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private Long doctor_Id;

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
    private int ratings;

    @Column(name = "appointmentList", nullable = false, unique = false)
    private List<String> appointmentList;

    @Column(name = "status", nullable = false, unique = false)
    private String status;

    public List<String> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<String> appointmentList) {
        this.appointmentList = appointmentList;
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

    public Long getDoctor_Id() {
        return doctor_Id;
    }

    public void setDoctor_Id(Long doctor_Id) {
        this.doctor_Id = doctor_Id;
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

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
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
