package com.fsf.habitup.Service;

import java.security.Permission;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fsf.habitup.DTO.AdminRequest;
import com.fsf.habitup.DTO.AuthResponseAdmin;
import com.fsf.habitup.DTO.LoginRequest;
import com.fsf.habitup.DTO.OtpVerificationReuest;
import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.Enums.DocumentStatus;
import com.fsf.habitup.Enums.PermissionType;
import com.fsf.habitup.Enums.UserType;
import com.fsf.habitup.Exception.ApiException;
import com.fsf.habitup.Exception.InvalidOtpException;
import com.fsf.habitup.Repository.AdminRepository;
import com.fsf.habitup.Repository.DoctorRepository;
import com.fsf.habitup.Repository.DocumentsRepository;
import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.Security.JwtTokenProvider;
import com.fsf.habitup.entity.Admin;
import com.fsf.habitup.entity.Doctor;
import com.fsf.habitup.entity.Documents;
import com.fsf.habitup.entity.User;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final OtpService otpService;
    private final DocumentsRepository documentsRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AdminServiceImpl(AdminRepository adminRepository, DoctorRepository doctorRepository,
            DocumentsRepository documentsRepository, OtpService otpService, PasswordEncoder passwordEncoder,
            UserRepository userRepository, JwtTokenProvider jwtTokenProvider,
            AuthenticationManager authenticationManager) {
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.documentsRepository = documentsRepository;
        this.otpService = otpService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public boolean verifyUserForDoctor(Long userId) {
        // Fetch user by ID
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user is already a doctor
        if (user.getUserType() == UserType.Doctor) {
            throw new RuntimeException("User is already a doctor");
        }

        // Fetch documents of the user
        List<Documents> documents = documentsRepository.findByUser_UserId(userId);

        // Validate documents
        if (!areDocumentsValid(documents)) {
            throw new RuntimeException("User's documents are not fully approved");
        }

        // Convert user to doctor
        Doctor doctor = new Doctor();
        doctor.setDoctorName(user.getName());
        doctor.setEmailId(user.getEmail());
        doctor.setPhoneNo(user.getPhoneNo());
        doctor.setGender(user.getGender());
        doctor.setSpecialization("General"); // Default, can be updated later
        doctor.setYearsOfExperience((byte) 0); // Default, can be updated later
        doctor.setAvailabilitySchedule(null);
        doctor.setConsultationFee(0);
        doctor.setRatings(0.0f);
        doctor.setUserType(UserType.Doctor);
        doctor.setDocumentStatus(DocumentStatus.APPROVED);
        doctor.setUser(user); // Link to user

        // Save doctor entity
        doctorRepository.save(doctor);

        // Update user type to Doctor
        user.setUserType(UserType.Doctor);
        userRepository.save(user);

        return true;
    }

    private boolean areDocumentsValid(List<Documents> documents) {
        return documents != null && documents.stream()
                .allMatch(doc -> doc.getStatus() == DocumentStatus.APPROVED);
    }

    @Override
    public Admin addAdmin(AdminRequest request) {
        Admin newAdmin = new Admin();
        newAdmin.setEmail(request.getEmail());
        newAdmin.setName(request.getName());
        newAdmin.setGender(request.getGender());
        newAdmin.setUserType(request.getUserType());
        newAdmin.setLastLogin(LocalDateTime.now());

        // Step 2: Encode the password from the request
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        newAdmin.setPassword(encodedPassword);

        // Step 3: Handle OTP verification (if necessary)
        OtpVerificationReuest otpRequest = request.getOtpVerificationRequest();
        if (otpRequest != null) {
            // Generate OTP and validate it before proceeding
            boolean isOtpValid = otpService.validateOtp(request.getEmail(), otpRequest.getOtp());
            if (!isOtpValid) {
                // Logging invalid OTP attempt (could also log email for audit purposes)
                System.err.println("Invalid OTP attempt for email: " + request.getEmail());
                throw new InvalidOtpException("Invalid OTP! Please try again.");
            }
        }

        // Step 4: Save the new admin to the database
        Admin savedAdmin = adminRepository.save(newAdmin);
        System.out.println("New admin created successfully: " + savedAdmin.getEmail());
        return savedAdmin;
    }

    @Override
    public String sendOtp(String email) {
        otpService.generateAndSendOtp(email);
        return "OTP sent to " + email + ". Please Verify";
    }

    @Override
    public AuthResponseAdmin AdminLogin(LoginRequest request) {

        try {
            // Authenticate admin using AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            // Set authentication in security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token for authenticated admin
            String token = jwtTokenProvider.generateToken(request.getEmail());

            return new AuthResponseAdmin(token, adminRepository.findByEmail(request.getEmail()));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @Override
    public Admin getAdminById(Long adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new ApiException("Admin not found with ID: " + adminId));
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public boolean removeAdmin(Long adminId) {
        if (!adminRepository.existsById(adminId)) {
            throw new ApiException("Admin not found with ID: " + adminId);
        }

        adminRepository.deleteById(adminId);
        return true;
    }

    @Override
    public boolean updateAccountStatus(Long userId, AccountStatus status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("User not found with ID: " + userId));

        user.setAccountStatus(status);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ApiException("User not found with ID: " + userId);
        }

        userRepository.deleteById(userId);
        return true;
    }

    @Override
    public User getUserDetails(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("User not found with ID: " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByType(UserType userType) {
        return userRepository.findByUserType(userType);
    }

    @Override
    public boolean assignUserType(Long userId, UserType userType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("User not found with ID: " + userId));

        user.setUserType(userType);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean hasPermission(Long userId, UserType requiredUserType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("User not found with ID: " + userId));

        return user.getUserType() == requiredUserType;
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public boolean rejectDoctor(Long doctorId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addDoctor(Doctor doctor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Documents> getPendingDocuments() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean updateDocumentStatus(Long documentId, DocumentStatus status) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean lockUserAccount(Long userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean unlockUserAccount(Long userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean resetPassword(Long userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean grantPermissionToUser(Long userId, PermissionType permissionName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean revokePermissionFromUser(Long userId, PermissionType permissionName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean grantPermissionToDoctor(Long doctorId, PermissionType permissionName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean revokePermissionFromDoctor(Long doctorId, PermissionType permissionName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Permission> getPermissionsForUser(Long userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Permission> getPermissionsForDoctor(Long doctorId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean checkUserPermission(Long userId, PermissionType permissionName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean checkDoctorPermission(Long doctorId, PermissionType permissionName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasPermission(Long adminId, String permissionName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void viewSystemLogs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void viewErrorLogs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateSystemSettings() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void checkApplicationHealth() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void manageNotifications() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
