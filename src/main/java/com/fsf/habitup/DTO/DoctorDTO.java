package com.fsf.habitup.DTO;

import com.fsf.habitup.Enums.DocumentStatus;
import com.fsf.habitup.Enums.Gender;
import com.fsf.habitup.Enums.UserType;

public class DoctorDTO {
    private String doctorName;
    private String emailId;
    private Long phoneNo;
    private Gender gender;
    private String specialization;
    private byte yearsOfExperience;
    private UserType userType;
    private DocumentStatus documentStatus;

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
    public Gender getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(Gender gender) {
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
     * @return UserType return the userType
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
    }

}
