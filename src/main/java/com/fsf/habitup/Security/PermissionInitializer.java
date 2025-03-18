package com.fsf.habitup.Security;



import org.springframework.stereotype.Component;


import com.fsf.habitup.Service.PermissionService;


import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PermissionInitializer {

    private static final Logger logger = LoggerFactory.getLogger(PermissionInitializer.class);

    private final PermissionService permissionService;

    public PermissionInitializer(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostConstruct
    public void initializePermissions() {
        logger.info("Starting permission initialization...");
        permissionService.initializePermissions();
        logger.info("Permission initialization completed successfully.");
    }
}
