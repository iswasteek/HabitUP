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

    @Column(name = "yearsofexperience", nullable = false, unique = false)
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

    /**
     * @return Long return the doctor_Id
     */
    public Long getDoctor_Id() {
        return doctor_Id;
    }

    /**
     * @param doctor_Id the doctor_Id to set
     */
    public void setDoctor_Id(Long doctor_Id) {
        this.doctor_Id = doctor_Id;
    }

    /**
     * @return String return the doctorName
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * @param doctorName the doctorName to set
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    /**
     * @return String return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId the emailId to set
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * @return Long return the phoneNo
     */
    public Long getPhoneNo() {
        return phoneNo;
    }

    /**
     * @param phoneNo the phoneNo to set
     */
    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * @return String return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return String return the specialization
     */
    public String getSpecialization() {
        return specialization;
    }

    /**
     * @param specialization the specialization to set
     */
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    /**
     * @return byte return the yearsOfExperience
     */
    public byte getYearsOfExperience() {
        return yearsOfExperience;
    }

    /**
     * @param yearsOfExperience the yearsOfExperience to set
     */
    public void setYearsOfExperience(byte yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    /**
     * @return Date return the availabilitySchedule
     */
    public Date getAvailabilitySchedule() {
        return availabilitySchedule;
    }

    /**
     * @param availabilitySchedule the availabilitySchedule to set
     */
    public void setAvailabilitySchedule(Date availabilitySchedule) {
        this.availabilitySchedule = availabilitySchedule;
    }

    /**
     * @return int return the consultationFee
     */
    public int getConsultationFee() {
        return consultationFee;
    }

    /**
     * @param consultationFee the consultationFee to set
     */
    public void setConsultationFee(int consultationFee) {
        this.consultationFee = consultationFee;
    }

    /**
     * @return int return the ratings
     */
    public int getRatings() {
        return ratings;
    }

    /**
     * @param ratings the ratings to set
     */
    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    /**
     * @return List<String> return the appointmentList
     */
    public List<String> getAppointmentList() {
        return appointmentList;
    }

    /**
     * @param appointmentList the appointmentList to set
     */
    public void setAppointmentList(List<String> appointmentList) {
        this.appointmentList = appointmentList;
    }

    /**
     * @return String return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
