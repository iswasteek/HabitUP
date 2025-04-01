package com.fsf.habitup.DTO;

import com.fsf.habitup.Enums.PermissionType;

public class PermissionDTO {
    private Long id;
    private String name;

    public PermissionDTO(Long id, PermissionType name) {
        this.id = id;
        this.name = name.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
