package com.fsf.habitup.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.Enums.Gender;
import com.fsf.habitup.Enums.SubscriptionType;
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
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserId", unique = true, nullable = false)
	private Long userId;

	@Column(name = "Email", unique = true, nullable = false)
	public String email;

	@Column(name = "Name", unique = false, nullable = false)
	public String name;

	@Column(name = "Password", nullable = false)
	public String password;

	@Column(name = "joinDate", nullable = false)
	private LocalDateTime joinDate;

	@Column(name = "DOB", nullable = false)
	public Date dob;

	@Column(name = "phoneNumber", nullable = false, unique = true)
	public Long phoneNo;

	@Column(name = "AccountStatus", nullable = false)
	@Enumerated(EnumType.STRING)
	private AccountStatus accountStatus;

	@Column(name = "SubscriptionType", nullable = false)
	@Enumerated(EnumType.STRING)
	private SubscriptionType subscriptionType;

	@Column(name = "ProfilePhoto", nullable = false)
	private String profilePhoto;

	@Column(name = "userType", nullable = false)
	@Enumerated(EnumType.STRING)
	private UserType userType;

	@Column(name = "gender", nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * @return Long return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return AccountStatus return the accountStatus
	 */
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param accountStatus the accountStatus to set
	 */
	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * @return SubscriptionType return the subscriptionType
	 */
	public SubscriptionType getSubscriptionType() {
		return subscriptionType;
	}

	/**
	 * @param subscriptionType the subscriptionType to set
	 */
	public void setSubscriptionType(SubscriptionType subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	/**
	 * @return String return the profilePhoto
	 */
	public String getProfilePhoto() {
		return profilePhoto;
	}

	/**
	 * @param profilePhoto the profilePhoto to set
	 */
	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
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

	public Long getPhoneNumber() {
		return phoneNo;
	}

	public void setPhoneNo(Long phoneNo) {
		this.phoneNo = phoneNo;

	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDateTime joinDate) {
		this.joinDate = joinDate;
	}

}
