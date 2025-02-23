package com.fsf.habitup.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.Enums.SubscriptionType;
import com.fsf.habitup.Enums.UserType;
import com.fsf.habitup.Repository.UserRepository;
import com.fsf.habitup.entity.User;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    public ProfileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User updateUser(Long userId, User user) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setProfilePhoto(user.getProfilePhoto());
            existingUser.setAccountStatus(user.getAccountStatus());
            existingUser.setSubscriptionType(user.getSubscriptionType());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean updateProfilePhoto(Long userId, String newProfilePhoto) {
        Optional<User> userOp = userRepository.findById(userId);
        if (userOp.isPresent()) {
            User user = userOp.get();
            user.setProfilePhoto(newProfilePhoto);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAccountStatus(Long userId, AccountStatus accountStatus) {
        return userRepository.findById(userId)
                .map(user -> {
                    if (user.getAccountStatus() == AccountStatus.ACTIVE && accountStatus == AccountStatus.INACTIVE) {
                        user.setAccountStatus(AccountStatus.INACTIVE);
                    } else if (user.getAccountStatus() == AccountStatus.INACTIVE
                            && accountStatus == AccountStatus.ACTIVE) {
                        user.setAccountStatus(AccountStatus.ACTIVE);
                    } else {
                        return false; // No change needed
                    }
                    userRepository.save(user);
                    return true;
                })
                .orElse(false); // Return false if user not found
    }

    @Override
    public boolean updateSubscriptionType(Long userId, SubscriptionType newSubscriptionType, boolean isPaid) {
        return userRepository.findById(userId)
                .map(user -> {
                    if (isPaid) {
                        user.setSubscriptionType(SubscriptionType.PREMIUM);
                    } else {
                        user.setSubscriptionType(SubscriptionType.FREE);
                    }
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean updateUserType(Long userId, UserType userType) {
        return userRepository.findById(userId).map(user -> {
            Date dobDate = user.getDob(); // Get Date type DOB
            if (dobDate == null) {
                return false; // Can't determine UserType without DOB
            }

            // Convert Date to LocalDate
            LocalDate dob = dobDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int age = Period.between(dob, LocalDate.now()).getYears();

            UserType determinedType;
            if (age < 18) {
                determinedType = UserType.Child;
            } else if (age < 60) {
                determinedType = UserType.Adult;
            } else {
                determinedType = UserType.Elder;
            }

            user.setUserType(determinedType);
            userRepository.save(user);
            return true;
        }).orElse(false);
    }

}
