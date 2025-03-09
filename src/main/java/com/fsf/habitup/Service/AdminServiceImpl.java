package com.fsf.habitup.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fsf.habitup.DTO.AdminRequest;
import com.fsf.habitup.DTO.OtpVerificationReuest;
import com.fsf.habitup.Enums.DocumentStatus;
import com.fsf.habitup.Enums.UserType;
import com.fsf.habitup.Exception.InvalidOtpException;
import com.fsf.habitup.Repository.AdminRepository;
import com.fsf.habitup.Repository.DoctorRepository;
import com.fsf.habitup.Repository.DocumentsRepository;
import com.fsf.habitup.Repository.UserRepository;
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

    public AdminServiceImpl(AdminRepository adminRepository, DoctorRepository doctorRepository,
            DocumentsRepository documentsRepository, OtpService otpService, PasswordEncoder passwordEncoder,
            UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.documentsRepository = documentsRepository;
        this.otpService = otpService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
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
}
