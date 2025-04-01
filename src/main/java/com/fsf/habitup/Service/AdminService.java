package com.fsf.habitup.Service;

import java.util.List;

import com.fsf.habitup.DTO.AdminRequest;
import com.fsf.habitup.DTO.AuthResponseAdmin;
import com.fsf.habitup.DTO.LoginRequest;
import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.Enums.DocumentStatus;
import com.fsf.habitup.Enums.PermissionType;
import com.fsf.habitup.Enums.UserType;
import com.fsf.habitup.entity.Admin;
import com.fsf.habitup.entity.Doctor;
import com.fsf.habitup.entity.Documents;
import com.fsf.habitup.entity.User;

public interface AdminService {

    public boolean verifyUserForDoctor(Long userId);//

    Admin addAdmin(AdminRequest request);//

    public String sendOtp(String email);//

    public AuthResponseAdmin AdminLogin(LoginRequest request);

    Admin getAdminById(Long adminId);

    List<Admin> getAllAdmins();

    boolean removeAdmin(Long adminId);

    boolean updateAccountStatus(Long userId, AccountStatus status);

    boolean deleteUser(Long userId);

    User getUserDetails(Long userId);

    List<User> getAllUsers();

    List<User> getUsersByType(UserType userType);

    boolean assignUserType(Long userId, UserType userType);

    boolean hasPermission(Long userId, UserType requiredUserType);

    List<Doctor> getAllDoctors();

    boolean rejectDoctor(Long doctorId);

    List<Documents> getPendingDocuments();

    boolean updateDocumentStatus(Long documentId, DocumentStatus status);

//    boolean grantPermissionToUser(Long userId, PermissionType permissionName);
//
//    boolean revokePermissionFromUser(Long userId, PermissionType permissionName);
//
//    boolean grantPermissionToDoctor(Long doctorId, PermissionType permissionName);
//
//    boolean revokePermissionFromDoctor(Long doctorId, PermissionType permissionName);

    List<com.fsf.habitup.entity.Permission> getPermissionsForUser(Long userId);

    List<com.fsf.habitup.entity.Permission> getPermissionsForDoctor(Long doctorId);

    boolean checkUserPermission(Long userId, PermissionType permissionName);

    boolean checkDoctorPermission(Long doctorId, PermissionType permissionName);

    boolean hasPermission(Long adminId, String permissionName);

    List<String> viewSystemLogs();

    List<String> viewErrorLogs();

    void updateSystemSettings();

    void checkApplicationHealth();

    void manageNotifications();

}
