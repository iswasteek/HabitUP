package com.fsf.habitup.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fsf.habitup.Enums.Gender;
import com.fsf.habitup.Enums.UserType;
import com.fsf.habitup.Repository.AdminRepository;
import com.fsf.habitup.entity.Admin;

@Component
public class AdminSetUpRunner implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSetUpRunner(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if there is no admin in the system
        if (adminRepository.count() == 0) {
            // Create the first admin if no admins exist
            Admin admin = new Admin();
            admin.setEmail("mbasudev022@gmail.com"); // Set the email
            admin.setName("Basudev Naik"); // Set your admin name
            String encodedPassword = passwordEncoder.encode("Basudev@2004");
            admin.setPassword(encodedPassword); // Set the password (make sure to hash it)
            admin.setGender(Gender.MALE); // Set the gender or other attributes as needed
            admin.setUserType(UserType.Admin); // Set the user type as ADMIN
            admin.setLastLogin(LocalDateTime.now());

            // Save the first admin
            adminRepository.save(admin);

            System.out.println("First admin created successfully!");
        }
    }

}
