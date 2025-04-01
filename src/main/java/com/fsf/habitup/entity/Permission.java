package com.fsf.habitup.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fsf.habitup.Enums.PermissionType;

import jakarta.persistence.*;

@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permissionId", unique = true, nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private PermissionType name;  // Make sure this exists

//    @ManyToMany(mappedBy = "permissions")
//    // No @JoinTable here
//    private Set<User> users = new HashSet<>();
//
//    @ManyToMany(mappedBy = "permissions")
//    // Correct bidirectional mapping
//    private Set<Doctor> doctors = new HashSet<>();
//
//    @ManyToMany(mappedBy = "permissions")
//
//    private Set<Admin> admins = new HashSet<>();

    // Constructors
    public Permission() {
    }

    public Permission(PermissionType name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




    public PermissionType getName() {
        return name;
    }

    public void setName(PermissionType name) {
        this.name = name;
    }

//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }
//
//    public Set<Doctor> getDoctors() {
//        return doctors;
//    }
//
//    public void setDoctors(Set<Doctor> doctors) {
//        this.doctors = doctors;
//    }
//
//    public Set<Admin> getAdmins() {
//        return admins;
//    }
//
//    public void setAdmins(Set<Admin> admins) {
//        this.admins = admins;
//    }
}
