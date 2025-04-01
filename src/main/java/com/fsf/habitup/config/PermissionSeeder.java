package com.fsf.habitup.config;

import java.util.Arrays;
import java.util.List;

import com.fsf.habitup.Repository.DoctorRepository;
import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.entity.Doctor;
import com.fsf.habitup.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fsf.habitup.Enums.PermissionType;
import com.fsf.habitup.Repository.PermissionRepository;
import com.fsf.habitup.entity.Permission;

@Configuration
public class PermissionSeeder {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Bean
    public CommandLineRunner seedPermissions(PermissionRepository permissionRepository) {
        return args -> {
            if (permissionRepository.count() == 0) { // Avoid duplicates
                List<Permission> permissions = Arrays.stream(PermissionType.values())
                        .map(Permission::new)
                        .toList();
                permissionRepository.saveAll(permissions);
            }
        };
    }




}
