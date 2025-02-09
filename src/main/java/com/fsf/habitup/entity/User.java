package com.fsf.habitup.entity;

//import java.util.Collection;

//import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserId", unique = true, nullable = false)
	private Long user_id;

	@Column(name = "Email", unique = true, nullable = false)
	public String email;

	@Column(name = "Name", unique = false, nullable = false)
	public String name;

	@Column(name = "Password", nullable = false)
	public String password;

	@Column(name = "DOB", nullable = false)
	public String dob;

	@Column(name = "JoinDate", nullable = false)
	public String joinDate;

	public Long getUser_id() {
		return user_id;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getDob() {
		return dob;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public User orElseThrow(Object object) {

		throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
	}

	// public Collection<? extends GrantedAuthority> getAuthorities() {
	//
	// return null;
	// }

	// public static Object withUsername(String string) {
	//
	// return null;
	// }

}
