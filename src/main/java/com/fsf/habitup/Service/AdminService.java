package com.fsf.habitup.Service;

import com.fsf.habitup.DTO.AdminRequest;
import com.fsf.habitup.entity.Admin;

public interface AdminService {

    public boolean verifyUserForDoctor(Long userId);

    Admin addAdmin(AdminRequest request);

    public String sendOtp(String email);

}
