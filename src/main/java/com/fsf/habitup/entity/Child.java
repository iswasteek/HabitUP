package com.fsf.habitup.entity;

import jakarta.persistence.*;

@Entity
@Table(name="child")
public class Child extends User {

	@Column(name = "childId", unique = true, nullable = false)
	private Long childId;

	@Column(name = "AccountStatus", nullable = false)
	private String accountStatus;

	@Column(name = "SubscriptionType", nullable = false)
	private String subscriptionType;

	@Column(name = "ProfilePhoto", nullable = false)
	public String profilePhoto;

	@OneToOne
	@JoinColumn(name = "userId", referencedColumnName = "userId", unique = true)
	private User user;

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Long getChildId() {
		return childId;
	}

	public void setChildId(Long childId) {
		this.childId = childId;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
