package com.fsf.habitup.Security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fsf.habitup.Enums.PermissionType;
import com.fsf.habitup.Enums.UserType;
import com.fsf.habitup.Repository.AdminRepository;
import com.fsf.habitup.Repository.DoctorRepository;
import com.fsf.habitup.Repository.PermissionRepository;
import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.entity.Admin;
import com.fsf.habitup.entity.Doctor;
import com.fsf.habitup.entity.Permission;
import com.fsf.habitup.entity.User;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Component
public class PermissionInitializer {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;

    public PermissionInitializer(UserRepository userRepository, PermissionRepository permissionRepository,
            AdminRepository adminRepository, DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
    }

    @PostConstruct
    @Transactional
    public void initializeUserPermissions() {
        // Get all users
        List<User> users = userRepository.findAll();

        // Get common permissions for users
        Set<Permission> commonPermissions = new HashSet<>();
        commonPermissions.add(permissionRepository.findByName(PermissionType.MANAGE_USERS));
        commonPermissions.add(permissionRepository.findByName(PermissionType.VIEW_USERS));
        commonPermissions.add(permissionRepository.findByName(PermissionType.RESET_USER_PASSWORDS));
        commonPermissions.add(permissionRepository.findByName(PermissionType.MANAGE_SUBSCRIPTIONS));
        commonPermissions.add(permissionRepository.findByName(PermissionType.ACTIVATE_USERS));

        // Assign common permissions to users
        for (User user : users) {
            user.getPermissions().addAll(commonPermissions);
            userRepository.save(user);
        }
    }

    @PostConstruct
    @Transactional
    public void initializeAdminPermissions() {
        System.out.println("Initializing admin permissions...");

        List<Admin> admins = adminRepository.findAllByUserType(UserType.Admin);
        List<Permission> allPermissions = permissionRepository.findAll();

        System.out.println("Found " + admins.size() + " admins and " + allPermissions.size() + " permissions.");

        if (admins.isEmpty() || allPermissions.isEmpty()) {
            System.out.println("No admins or no permissions found! Nothing to update.");
            return;
        }

        for (Admin admin : admins) {
            System.out.println(
                    "Assigning " + allPermissions.size() + " permissions to admin with ID: " + admin.getAdminId());
            admin.setPermissions(new HashSet<>(allPermissions));
            adminRepository.save(admin);
        }

        System.out.println("Admin permissions initialization complete.");
    }

    @PostConstruct
    @Transactional
    public void initializeDoctorPermissions() {
        // Get all doctors
        List<Doctor> doctors = doctorRepository.findAll();

        // Fetch required permissions
        Set<Permission> doctorPermissions = new HashSet<>();
        doctorPermissions.add(permissionRepository.findByName(PermissionType.MANAGE_DOCTORS));
        doctorPermissions.add(permissionRepository.findByName(PermissionType.VIEW_DOCTORS));
        doctorPermissions.add(permissionRepository.findByName(PermissionType.ACCESS_API));

        // Assign permissions to each doctor
        for (Doctor doctor : doctors) {
            doctor.getPermissions().addAll(doctorPermissions);
            doctorRepository.save(doctor);
        }
    }
}
