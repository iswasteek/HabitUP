package com.fsf.habitup.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fsf.habitup.Enums.PermissionType;
import com.fsf.habitup.Repository.PermissionRepository;
import com.fsf.habitup.entity.Permission;

@Configuration
public class PermissionSeeder {

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
