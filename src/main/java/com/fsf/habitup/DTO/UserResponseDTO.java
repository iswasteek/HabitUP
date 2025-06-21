package com.fsf.habitup.DTO;

import com.fsf.habitup.Enums.*;
import com.fsf.habitup.entity.User;

public class UserResponseDTO {
    private Long userId;
    private String email;
    private String name;
    private String joinDate;
    private String dob;
    private Long phoneNo;
    private AccountStatus accountStatus;
    private SubscriptionType subscriptionType;
    private String profilePhoto;
    private UserType userType;
    private Gender gender;

    public UserResponseDTO(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.joinDate = user.getJoinDate();
        this.dob = user.getDob();
        this.phoneNo = user.getPhoneNo();
        this.accountStatus = user.getAccountStatus();
        this.subscriptionType = user.getSubscriptionType();
        this.userType = user.getUserType();
        this.gender = user.getGender();

        // Construct proper photo URL
        if (user.getProfilePhoto() != null && !user.getProfilePhoto().isEmpty()) {
            this.profilePhoto = user.getProfilePhoto();
        }
    }

    // Getters and setters for all fields
    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public String getDob() {
        return dob;
    }

    public Long getPhoneNo() {
        return phoneNo;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public UserType getUserType() {
        return userType;
    }

    public Gender getGender() {
        return gender;
    }

    // Setters if needed

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}