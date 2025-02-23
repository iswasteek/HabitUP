package com.fsf.habitup.Service;

import java.util.List;

import com.fsf.habitup.Enums.AccountStatus;
import com.fsf.habitup.Enums.SubscriptionType;
import com.fsf.habitup.Enums.UserType;
import com.fsf.habitup.entity.User;

public interface ProfileService {

    User updateUser(Long userId, User user);

    User getUserById(Long userId);

    List<User> getAllUsers();

    boolean updateProfilePhoto(Long userId, String newProfilePhoto);

    boolean updateAccountStatus(Long userId, AccountStatus accountStatus);

    boolean updateSubscriptionType(Long userId, SubscriptionType subscriptionType, boolean paymentStatus);

    boolean updateUserType(Long userId, UserType userType);
}
