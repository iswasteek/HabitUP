package com.fsf.habitup.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fsf.habitup.Repository.HabitRepo;
import com.fsf.habitup.entity.Habit;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fsf.habitup.DTO.AuthResponse;
import com.fsf.habitup.DTO.ForgetPasswordRequest;
import com.fsf.habitup.DTO.LoginRequest;
import com.fsf.habitup.DTO.LogoutResponse;
import com.fsf.habitup.DTO.OtpRegisterRequest;
import com.fsf.habitup.DTO.OtpVerificationReuest;
import com.fsf.habitup.DTO.RegisterRequest;
import com.fsf.habitup.DTO.UpdateUserDTO;
import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.Enums.SubscriptionType;
import com.fsf.habitup.Enums.UserType;
import com.fsf.habitup.Exception.ApiException;
import com.fsf.habitup.Repository.DocumentsRepository;
import com.fsf.habitup.Repository.PasswordResetTokenRepository;
import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.Security.JwtTokenProvider;
import com.fsf.habitup.entity.PasswordResetToken;
import com.fsf.habitup.entity.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService {

    private final OtpService otpService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final PasswordResetTokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final DocumentsRepository documentsRepository;

    private final JavaMailSender mailSender;
    private final FileStorageService fileStorageService;
    private final HabitRepo habitRepo;

    public UserServiceImpl(AuthenticationManager authenticationManager, DocumentsRepository documentsRepository,
            FileStorageService fileStorageService, JwtTokenProvider jwtTokenProvider, JavaMailSender mailSender,
            OtpService otpService, PasswordEncoder passwordEncoder, PasswordResetTokenRepository tokenRepository,
            UserRepository userRepository, HabitRepo habitRepo) {
        this.authenticationManager = authenticationManager;
        this.documentsRepository = documentsRepository;
        this.fileStorageService = fileStorageService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mailSender = mailSender;
        this.otpService = otpService;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.habitRepo = habitRepo;
    }

    @Override
    public String forgotPassword(ForgetPasswordRequest forgetPasswordRequest) {

        if (!otpService.validateOtp(forgetPasswordRequest.getEmail(), forgetPasswordRequest.getOtp())) {
            return "Invalid or expired OTP";
        }

        User user = userRepository.findByEmail(forgetPasswordRequest.getEmail());
        if (user == null) {
            return "User not found";
        }
        String hashedPassword = passwordEncoder.encode(forgetPasswordRequest.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);

        return "Password reset successful!";
    }

    @Override
    public String sendOtp(String email) {
        if (userRepository.findByEmail(email) != null) {
            throw new ApiException("This email is already registered");
        }
        // Generate and send OTP
        otpService.generateAndSendOtp(email);
        return "OTP sent to " + email + ". Please verify before completing registration.";
    }

    @Override
    public String SendOTPForForgotPassword(String email) {
        if (userRepository.findByEmail(email) == null) {
            throw new ApiException("Register Yourself first");
        }

        // Generate and send OTP
        otpService.generateAndSendOtp(email);
        return "OTP sent to " + email + ". Please use this OTP for password change.";

    }

    @Override
    public String verifyOtpAndCreateUser(OtpRegisterRequest request) {
        OtpVerificationReuest otpRequest = request.getOtpVerificationRequest();
        RegisterRequest registerRequest = request.getRegisterRequest();

        if (!otpService.validateOtp(otpRequest.getEmail(), otpRequest.getOtp())) {
            return "Invalid or expired OTP";
        }

        if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
            return "This email is already registered. Please log in.";
        }
        if (userRepository.existsByPhoneNo(Long.parseLong(registerRequest.getPhoneNo()))) {
            return "This phone number is already registered. Please use another number.";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setJoinDate(LocalDate.now().format(formatter));
        user.setDob(registerRequest.getDateOfBirth());

        LocalDate dob = LocalDate.parse(registerRequest.getDateOfBirth(), formatter);
        int age = Period.between(dob, LocalDate.now()).getYears();

        if (age < 18) {
            user.setUserType(UserType.Child);
        } else if (age <= 60) {
            user.setUserType(UserType.Adult);
        } else {
            user.setUserType(UserType.Elder);
        }

        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setPhoneNo(Long.parseLong(registerRequest.getPhoneNo()));
        user.setSubscriptionType(SubscriptionType.FREE);
        user.setGender(registerRequest.getGender());
        user.setAccountStatus(AccountStatus.ACTIVE);

        // ✅ Save the user first (so we can associate habits)
        User savedUser = userRepository.save(user);

        // ✅ Fetch user-specific and universal default habits
        List<Habit> defaultHabits = habitRepo.findByIsDefaultTrueAndUserType(savedUser.getUserType());
        List<Habit> universalHabits = habitRepo.findByIsDefaultTrueAndUserTypeIsNull();

        // ✅ Add to user
        Set<Habit> allHabits = new HashSet<>();
        allHabits.addAll(defaultHabits);
        allHabits.addAll(universalHabits);

        savedUser.setHabits(allHabits);

        // ✅ Save the user again with assigned habits
        userRepository.save(savedUser);

        return "Registration successful!";
    }


    @Override
    public AuthResponse authenticateUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new ApiException("User not found");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String token = jwtTokenProvider.generateToken(request.getEmail());
        return new AuthResponse(token, user);
    }

    @Override
    public User updateUser(String email, UpdateUserDTO updatedUserDTO, MultipartFile profilePhoto) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            throw new ApiException("User not found");
        }

        existingUser.setName(updatedUserDTO.getName());
        existingUser.setDob(updatedUserDTO.getDob());
        existingUser.setPhoneNo(updatedUserDTO.getPhoneNo());
        existingUser.setGender(updatedUserDTO.getGender());

        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            String savedPath = fileStorageService.storeFile(profilePhoto, "profile_photos");
            existingUser.setProfilePhoto(savedPath);
        }

        return userRepository.save(existingUser);
    }

    @Override
    public User findUserByEmail(String email) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            throw new ApiException("User not found!");
        }
        return existingUser;
    }

    @Override
    public boolean deleteUser(String email) {
        if (userRepository.findByEmail(email) == null) {
            return false;
        }
        userRepository.deleteByEmail(email);
        return true;
    }

    @Override
    public boolean sendPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }

        // Delete existing token for this user (if any)
        PasswordResetToken existingToken = tokenRepository.findByUser(user);
        if (existingToken != null) {
            tokenRepository.delete(existingToken);
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(15);

        PasswordResetToken resetToken = new PasswordResetToken(token, user, expiryDate);
        tokenRepository.save(resetToken);
        sendEmail(user.getEmail(), token);

        return true;
    }


    private void sendEmail(String recipientEmail, String token) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(recipientEmail);
            helper.setSubject("Password Reset Request");

            String htmlContent = "<html>" +
                    "<body style='font-family: Arial, sans-serif;'>" +
                    "<h2>Password Reset Token</h2>" +
                    "<p>Use this token for new password generation:</p>" +
                    "<div style='padding:10px; border:1px solid #ccc; display:inline-block; font-size:16px; color:#333; background-color:#f9f9f9;'><strong>" + token + "</strong></div>" +
                    "<br><br>" +
                    "<p>If you did not request this, please ignore this email.</p>" +
                    "<br>" +
                    "<p>Thanks,<br><strong>Team habitUp</strong></p>" +
                    "</body></html>";

            helper.setText(htmlContent, true); // true = isHtml

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // handle error
            e.printStackTrace();
        }
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false; // Token invalid or expired
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword)); // Encrypt password
        userRepository.save(user);

        // Remove the token after successful reset
        tokenRepository.delete(resetToken);
        return true;
    }

    @Override
    public boolean updateAccountStatus(Long userId, AccountStatus accountStatus) {

        // Fetch the user and update status
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("User not found!"));

        if (user.getAccountStatus() == accountStatus) {
            return false; // No change needed
        }

        user.setAccountStatus(accountStatus);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean updateSubscriptionType(Long userId, SubscriptionType subscriptionType, boolean paymentStatus) {

        return userRepository.findById(userId)
                .map(user -> {
                    user.setSubscriptionType(paymentStatus ? SubscriptionType.PREMIUM : SubscriptionType.FREE);
                    userRepository.save(user);
                    return true;
                })
                .orElseThrow(() -> new ApiException("User not found!"));
    }

    @Override
    public LogoutResponse logout(String email, HttpServletResponse response) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new ApiException("User not found.");
        }

        // Clear the JWT token by removing it from cookies
        Cookie jwtCookie = new Cookie("Authorization", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Expire the cookie immediately
        response.addCookie(jwtCookie);

        // Optional: Update user account status to INACTIVE
        user.setAccountStatus(AccountStatus.INACTIVE);
        userRepository.save(user);

        // Return logout response
        return new LogoutResponse("User logged out successfully.", HttpStatus.OK.value());
    }

    // @Override
    // public boolean sendDocumentsForAdminVerification(Long userId,
    // List<MultipartFile> documents,
    // List<String> documentTypes) {
    // User user = userRepository.findById(userId).orElseThrow(() -> new
    // RuntimeException("User not found"));

    // if (documents == null || documents.isEmpty() || documentTypes == null
    // || documents.size() != documentTypes.size()) {
    // throw new RuntimeException("Invalid documents or document types");
    // }

    // for (int i = 0; i < documents.size(); i++) {
    // MultipartFile file = documents.get(i);
    // String docType = documentTypes.get(i);

    // String storedFileUrl = saveFileAndGetUrl(file); // Placeholder method

    // Documents doc = new Documents();
    // doc.setDocumentName(file.getOriginalFilename());
    // doc.setDocumentType(docType);
    // doc.setDocumentUrl(storedFileUrl);
    // doc.setStatus(DocumentStatus.PENDING);
    // doc.setUser(user);

    // documentsRepository.save(doc);
    // }
    // return true;
    // }

    // // Placeholder for actual file storage logic (local/cloud)
    // private String saveFileAndGetUrl(MultipartFile file) {
    // // Implement your file saving here and return the file URL or path
    // // For now, just returning the original filename as dummy URL
    // return "/uploads/" + file.getOriginalFilename();
    // }

}
