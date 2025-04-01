package com.fsf.habitup.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsf.habitup.Enums.PermissionType;
import com.fsf.habitup.entity.Permission;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByName(PermissionType permissionType);

}
