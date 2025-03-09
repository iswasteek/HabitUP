package com.fsf.habitup.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsf.habitup.Enums.Gender;
import com.fsf.habitup.Enums.UserType;

public class AdminRequest {

    private String email;
    private String password;
    private Gender gender;
    private UserType userType;
    private String name;

    @JsonProperty("otpVerificationRequest")
    private OtpVerificationReuest otpVerificationRequest;

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Gender return the gender
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

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public OtpVerificationReuest getOtpVerificationRequest() {
        return otpVerificationRequest;
    }

    public void setOtpVerificationRequest(OtpVerificationReuest otpVerificationRequest) {
        this.otpVerificationRequest = otpVerificationRequest;
    }

}
