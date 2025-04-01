package com.fsf.habitup.DTO;

import com.fsf.habitup.entity.Admin;

import java.util.Set;
import java.util.stream.Collectors;

public class AdminResponse {
    private Long adminId;
    private String email;
    private String name;
    private Set<PermissionDTO> permissions;

    public static AdminResponse fromEntity(Admin admin) {
        AdminResponse dto = new AdminResponse();
        dto.setAdminId(admin.getAdminId());
        dto.setEmail(admin.getEmail());
        dto.setName(admin.getName());
        dto.setPermissions(
                admin.getPermissions().stream()
                        .map(permission -> new PermissionDTO(permission.getId(), permission.getName()))
                        .collect(Collectors.toSet())
        );
        return dto;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PermissionDTO> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionDTO> permissions) {
        this.permissions = permissions;
    }
}
