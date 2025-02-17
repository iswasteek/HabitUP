package com.fsf.habitup.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
// @DiscriminatorValue("child")
// @Table(name="child")
public class Child extends User {

	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "childId", unique = true, nullable = false)
	private Long childId;

	@Column(name = "AccountStatus", nullable = false)
	private String accountStatus;

	@Column(name = "SubscriptionType", nullable = false)
	private String subscriptionType;

	@Column(name = "ProfilePhoto", nullable = false)
	public String profilephoto;

	@OneToOne
	@JoinColumn(name = "userId", referencedColumnName = "userId", unique = true)
	private User user;

	public Long getChildId() {
		return childId;
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

	public void setChildId(Long childId) {
		this.childId = childId;
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
