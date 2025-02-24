package com.fsf.habitup.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fsf.habitup.DTO.LoginRequest;
import com.fsf.habitup.DTO.RegisterRequest;
import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.Enums.SubscriptionType;
import com.fsf.habitup.Enums.UserType;
import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.entity.User;

public interface UserService {

    @Autowired
    UserRepository userRepository = null;

    public String registerUser(RegisterRequest request);

    public String authenticateUser(LoginRequest request);

    public User updateUser(String email, User user);

    public boolean deleteUser(String email);

    public User getUserByEmail(String email);

    public List<User> getAllUsers();

    public boolean updateUserPassword(String email, String newPassword);

    public boolean updateUserPhoneNo(String email, Long newPhoneNo);

    public boolean updateUserDob(String email, Date newDob);

    public boolean updateUserName(String email, String Name);

    public default User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    boolean updateUserType(Long userId, UserType userType);

    boolean updateProfilePhoto(Long userId, String newProfilePhoto);

    boolean updateAccountStatus(Long userId, AccountStatus accountStatus);

    boolean updateSubscriptionType(Long userId, SubscriptionType subscriptionType, boolean paymentStatus);

}
