package com.fsf.habitup.entity;

import jakarta.persistence.Column;
//import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;



@Entity
//@DiscriminatorValue("adult")
//@Table(name="adult")
public class Adult extends User {
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adultid", unique = true, nullable = false)
    private Long adultId;

    public Long getAdultId() {
		return adultId;
	}


	public String getAccountStatus() {
		return accountStatus;
	}


	public String getSubscriptionType() {
		return subscriptionType;
	}


	public String getProfilePhoto() {
		return profilePhoto;
	}


	public User getUser() {
		return user;
	}


	public void setAdultId(Long adultId) {
		this.adultId = adultId;
	}


	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}


	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}


	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Column(name = "AccountStatus", nullable = false)
    private String accountStatus;

    @Column(name = "SubscriptionType", nullable = false)
    private String subscriptionType;

    @Column(name = "ProfilePhoto", nullable = false)
    public String profilePhoto;

   
    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", unique = true)
    private User user;

  

	
    
}
