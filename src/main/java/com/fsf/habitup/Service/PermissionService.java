
package com.fsf.habitup.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PermissionService {

    private static final Logger logger = LoggerFactory.getLogger(PermissionService.class);

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;

    public PermissionService(
            @Lazy UserRepository userRepository,
            @Lazy PermissionRepository permissionRepository,
            @Lazy AdminRepository adminRepository,
            @Lazy DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public void initializePermissions() {
        initializeUserPermissions();
        initializeAdminPermissions();
        initializeDoctorPermissions();
    }

    private void initializeUserPermissions() {
        logger.info("Initializing user permissions...");
        List<User> users = userRepository.findAll();
        Set<Permission> commonPermissions = new HashSet<>();
        addPermission(commonPermissions, PermissionType.MANAGE_USERS);
        addPermission(commonPermissions, PermissionType.VIEW_USERS);
        addPermission(commonPermissions, PermissionType.RESET_USER_PASSWORDS);
        addPermission(commonPermissions, PermissionType.MANAGE_SUBSCRIPTIONS);
        addPermission(commonPermissions, PermissionType.ACTIVATE_USERS);
        addPermission(commonPermissions, PermissionType.LOCK_UNLOCK_USERS);
        addPermission(commonPermissions, PermissionType.RESET_USER_PASSWORDS);
        addPermission(commonPermissions, PermissionType.VIEW_SUBSCRIPTIONS);
        addPermission(commonPermissions, PermissionType.MANAGE_SUBSCRIPTIONS);
        addPermission(commonPermissions, PermissionType.EXPORT_HABIT_PROGRESS);
        addPermission(commonPermissions, PermissionType.MANAGE_FEEDBACK);
        addPermission(commonPermissions, PermissionType.MANAGE_HABIT_PROGRESS);
        addPermission(commonPermissions, PermissionType.VIEW_FEEDBACK);
        addPermission(commonPermissions, PermissionType.IMPORT_HABIT_PROGRESS);
        addPermission(commonPermissions, PermissionType.VIEW_THOUGHTS);

        for (User user : users) {
            user.getPermissions().addAll(commonPermissions);
            userRepository.save(user);
        }
        logger.info("User permissions assigned to {} users.", users.size());
    }

    private void initializeAdminPermissions() {
        logger.info("Initializing admin permissions...");
        List<Admin> admins = adminRepository.findAllByUserType(UserType.Admin);
        List<Permission> allPermissions = permissionRepository.findAll();

        if (admins.isEmpty() || allPermissions.isEmpty()) {
            logger.warn("No admins or permissions found. Skipping admin permission assignment.");
            return;
        }

        for (Admin admin : admins) {
            admin.setPermissions(new HashSet<>(allPermissions));
            adminRepository.save(admin);
        }
        logger.info("Admin permissions assigned to {} admins.", admins.size());
    }

    private void initializeDoctorPermissions() {
        logger.info("Initializing doctor permissions...");
        List<Doctor> doctors = doctorRepository.findAll();
        Set<Permission> doctorPermissions = new HashSet<>();
        addPermission(doctorPermissions, PermissionType.MANAGE_DOCTORS);
        addPermission(doctorPermissions, PermissionType.VIEW_DOCTORS);
        addPermission(doctorPermissions, PermissionType.VIEW_DOCUMENTS);
        addPermission(doctorPermissions, PermissionType.MANAGE_DOCUMENTS);
        addPermission(doctorPermissions, PermissionType.MANAGE_FEEDBACK);
        addPermission(doctorPermissions, PermissionType.VIEW_FEEDBACK);
        addPermission(doctorPermissions, PermissionType.VIEW_THOUGHTS);

        for (Doctor doctor : doctors) {
            doctor.getPermissions().addAll(doctorPermissions);
            doctorRepository.save(doctor);
        }
        logger.info("Doctor permissions assigned to {} doctors.", doctors.size());
    }

    private void addPermission(Set<Permission> permissions, PermissionType type) {
        Permission permission = permissionRepository.findByName(type);
        if (permission != null) {
            permissions.add(permission);
        } else {
            logger.warn("Permission not found: {}", type);
        }
    }
}

