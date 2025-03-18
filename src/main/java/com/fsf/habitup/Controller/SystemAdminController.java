package com.fsf.habitup.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsf.habitup.Service.AdminService;

@RestController
@RequestMapping("/habit/admin/system")
@PreAuthorize("hasAuthority('ADMIN')")
public class SystemAdminController {
    private final AdminService adminService;

    public SystemAdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/logs")
    @PreAuthorize("hasAuthority('ROLE_VIEW_LOGS')")
    public ResponseEntity<List<String>> viewSystemLogs() {
        List<String> logs = adminService.viewSystemLogs();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/logs/errors")
    @PreAuthorize("hasAuthority('ROLE_VIEW_ERROR_LOGS')")
    public ResponseEntity<List<String>> viewErrorLogs() {
        List<String> errorLogs = adminService.viewErrorLogs();
        return ResponseEntity.ok(errorLogs);
    }

    @PutMapping("/settings/update")
    @PreAuthorize("hasAuthority('ROLE_MANAGE_SETTINGS')")
    public ResponseEntity<String> updateSystemSettings() {
        adminService.updateSystemSettings();
        return ResponseEntity.ok("System settings updated successfully.");
    }

    @GetMapping("/health-check")
    @PreAuthorize("hasAuthority('ROLE_CHECK_HEALTH')")
    public ResponseEntity<String> checkApplicationHealth() {
        adminService.checkApplicationHealth();
        return ResponseEntity.ok("Application health check completed.");
    }
}
