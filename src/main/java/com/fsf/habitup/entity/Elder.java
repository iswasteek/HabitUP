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
//@Table(name="elder")
//@DiscriminatorValue("elder")
public class Elder extends User {
    //@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "elderid", unique = true, nullable = false)
    private Long elderId;

    @Column(name = "AccountStatus", nullable = false)
    private String accountStatus;

    @Column(name = "SubscriptionType", nullable = false)
    private String subscriptionType;

    @Column(name = "ProfilePhoto", nullable = false)
    public String profilephoto;

  
    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", unique = true)
    private User user;


	public Long getElderId() {
		return elderId;
	}


	public String getAccountStatus() {
		return accountStatus;
	}


	public String getSubscriptionType() {
		return subscriptionType;
	}


	public String getProfilephoto() {
		return profilephoto;
	}


	public User getUser() {
		return user;
	}


	public void setElderId(Long elderId) {
		this.elderId = elderId;
	}


	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}


	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}


	public void setProfilephoto(String profilephoto) {
		this.profilephoto = profilephoto;
	}


	public void setUser(User user) {
		this.user = user;
	}

    
}
