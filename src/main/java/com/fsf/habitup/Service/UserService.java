package com.fsf.habitup.Service;

import com.fsf.habitup.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.Enums.SubscriptionType;
import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.entity.User;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    @Autowired
    UserRepository userRepository = null;

    public String forgotPassword(ForgetPasswordRequest forgetPasswordRequest);

    public String sendOtp(String email);

    public String SendOTPForForgotPassword(String email);

    public AuthResponse authenticateUser(LoginRequest request);

    public User updateUser(String email, UpdateUserDTO updatedUserDTO, MultipartFile profilePhoto);

    public boolean deleteUser(String email);

    public User findUserByEmail(String email);

    public boolean sendPasswordResetToken(String email);

    public boolean resetPassword(String token, String newPassword);

    boolean updateAccountStatus(Long userId, AccountStatus accountStatus);

    boolean updateSubscriptionType(Long userId, SubscriptionType subscriptionType, boolean paymentStatus);

    public String verifyOtpAndCreateUser(OtpRegisterRequest request);

    public LogoutResponse logout(String email, HttpServletResponse response);
}
