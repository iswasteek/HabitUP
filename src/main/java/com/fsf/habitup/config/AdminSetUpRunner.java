package com.fsf.habitup.config;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fsf.habitup.Enums.Gender;
import com.fsf.habitup.Enums.UserType;
import com.fsf.habitup.Repository.AdminRepository;
import com.fsf.habitup.Repository.PermissionRepository;
import com.fsf.habitup.entity.Admin;
import com.fsf.habitup.entity.Permission;

@Component
public class AdminSetUpRunner implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final PermissionRepository permissionRepository;
    private final AdminProperties adminProperties;

    public AdminSetUpRunner(AdminRepository adminRepository, PasswordEncoder passwordEncoder,
                            PermissionRepository permissionRepository, AdminProperties adminProperties) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.permissionRepository = permissionRepository;
        this.adminProperties = adminProperties;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if there is no admin in the system
        if (adminRepository.count() == 0) {
            // Create the first admin if no admins exist
            Admin admin = new Admin();
            admin.setEmail(adminProperties.getEmail()); // Set the email
            admin.setName("Basudev Naik"); // Set your admin name
            String encodedPassword = passwordEncoder.encode(adminProperties.getPassword());
            admin.setPassword(encodedPassword); // Set the password (make sure to hash it)
            admin.setGender(Gender.MALE); // Set the gender or other attributes as needed
            admin.setUserType(UserType.Admin); // Set the user type as ADMIN
            admin.setLastLogin(LocalDateTime.now());

            Set<Permission> permissions = new HashSet<>(permissionRepository.findAll());
            admin.setPermissions(permissions);

            adminRepository.save(admin);

            System.out.println("First admin created successfully!");
        }
    }

}
